package com.ciel.loadstar.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.events.account.AccountEvent;
import com.ciel.loadstar.infrastructure.events.account.AccountEventType;
import com.ciel.loadstar.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.user.client.AuthServiceClient;
import com.ciel.loadstar.user.client.FolderServiceClient;
import com.ciel.loadstar.user.dto.input.CreateUser;
import com.ciel.loadstar.user.entity.User;
import com.ciel.loadstar.user.mq.producer.AccountEventProducer;
import com.ciel.loadstar.user.repository.ThemeRepository;
import com.ciel.loadstar.user.repository.UserRepository;
import com.ciel.loadstar.user.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    AccountEventProducer accountEventProducer;

    @Override
    public User queryById(Long id) {
        User user = accountRepository.selectById(id);
        if (user == null)
            throw new ObjectNotExistingException("用户不存在");
        return user;
    }

    @Override
    public User create(CreateUser user) {
        User existing = findByName(user.getUsername());
        Assert.isNull(existing, "用户已存在");

        ReturnModel<Long> remoteResult = authServiceClient.createUser(user);
        ApiReturnUtil.checkSuccess(remoteResult);

        User account = new User();
        account.setAccountId(remoteResult.getData());
        account.setNickname(user.getNickname());
        account.setUsername(user.getUsername());
        account.setLastSeen(new Date());

        accountRepository.insert(account);

        themeService.create(account);

        AccountEvent event = new AccountEvent(AccountEventType.CREATE);
        event.setId(remoteResult.getData().toString());
//        accountEventProducer.send(event);

        return account;
    }

    @Override
    public void delete(Long userId) {
        User user = queryById(userId);
        ReturnModel remoteResult = authServiceClient.deleteUser(user.getUsername());
        ApiReturnUtil.checkSuccess(remoteResult);

        accountRepository.deleteById(userId);

        AccountEvent event = new AccountEvent(AccountEventType.DELETE);
        event.setId(userId.toString());
//        accountEventProducer.send(event);
    }

    @Override
    public User findByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return accountRepository.selectOne(queryWrapper);
    }

    @Override
    public User findByAccountId(Long accountId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", accountId);
        return accountRepository.selectOne(queryWrapper);
    }

}
