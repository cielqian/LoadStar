package com.ciel.loadstar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.events.UserAccountEvent;
import com.ciel.loadstar.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtil;
import com.ciel.loadstar.infrastructure.utils.ReturnUtil;
import com.ciel.loadstar.user.client.AuthServiceClient;
import com.ciel.loadstar.user.client.FolderServiceClient;
import com.ciel.loadstar.user.entity.User;
import com.ciel.loadstar.user.dto.input.CreateUser;
import com.ciel.loadstar.user.repository.ThemeRepository;
import com.ciel.loadstar.user.repository.UserRepository;
import com.ciel.loadstar.user.service.AccountService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        ReturnUtil.checkSuccess(remoteResult);

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
        event.setProfile(ApplicationContextUtil.getActiveProfile());
        String jsonString = event.toJson();
        ListenableFuture future = kafkaTemplate.send(createFolderTopic, jsonString);
        future.addCallback(o -> log.info("send to topic UserAccountEvent success:" + jsonString)
                , throwable -> log.info("send to topic UserAccountEvent fail:" + jsonString));

//        remoteResult = folderServiceClient.createDefault(remoteResult.getData());
//        ReturnUtil.checkSuccess(remoteResult);

        return account;
    }

    @Override
    public void delete(Long userId) {
        User user = queryById(userId);
        ReturnModel remoteResult = authServiceClient.deleteUser(user.getUsername());
        ReturnUtil.checkSuccess(remoteResult);

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
