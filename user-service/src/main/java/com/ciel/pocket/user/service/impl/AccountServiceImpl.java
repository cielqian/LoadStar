package com.ciel.pocket.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.events.UserAccountEvent;
import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.client.AuthServiceClient;
import com.ciel.pocket.user.client.FolderServiceClient;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.dto.input.CreateUser;
import com.ciel.pocket.user.infrastructure.ApplicationContextUtils;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.repository.UserRepository;
import com.ciel.pocket.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/15
 * @Comment
 */
@Service
public class AccountServiceImpl extends ServiceImpl<UserRepository, User> implements AccountService {
    @Autowired
    UserRepository accountRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    AuthServiceClient authServiceClient;

    @Autowired
    FolderServiceClient folderServiceClient;

    @Autowired
    ThemeServiceImpl themeService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${loadstar.kafka.topic.UserAccountEvent}")
    private String createFolderTopic;

    @Override
    public User queryById(Long id) {
        User user = accountRepository.selectById(id);
        if (user == null)
            throw new ObjectNotExistingException("用户不存在");
        return user;
    }

    @Override
    public User create(CreateUser user) {
        User existing = accountRepository.findByUsername(user.getUsername());
        Assert.isNull(existing, "用户已存在");

        ReturnModel<Long> remoteResult = authServiceClient.createUser(user);
        ReturnUtils.checkSuccess(remoteResult);

        User account = new User();
        account.setAccountId(remoteResult.getData());
        account.setNickname(user.getNickname());
        account.setUsername(user.getUsername());
        account.setLastSeen(new Date());

        accountRepository.insert(account);

        themeService.create(account);

        UserAccountEvent event = new UserAccountEvent();
        event.setEvent("NEW");
        event.setId(remoteResult.getData().toString());
        event.setProfile(ApplicationContextUtils.getActiveProfile());
        String jsonString = event.toJson();
        ListenableFuture future = kafkaTemplate.send(createFolderTopic, jsonString);
        future.addCallback(o -> System.out.println("send to createFolderTopic success:" + remoteResult.getData())
                , throwable -> System.out.println("send to createFolderTopic fail:" + remoteResult.getData()));

//        remoteResult = folderServiceClient.createDefault(remoteResult.getData());
//        ReturnUtils.checkSuccess(remoteResult);

        return account;
    }

    @Override
    public void delete(Long userId) {
        User user = queryById(userId);
        ReturnModel remoteResult = authServiceClient.deleteUser(user.getUsername());
        ReturnUtils.checkSuccess(remoteResult);

        accountRepository.deleteById(userId);
    }

    @Override
    public User findByName(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public User findByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId);
    }

}
