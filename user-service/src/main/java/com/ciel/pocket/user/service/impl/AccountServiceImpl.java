package com.ciel.pocket.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.client.AuthServiceClient;
import com.ciel.pocket.user.client.FolderServiceClient;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.dto.input.CreateUser;
import com.ciel.pocket.user.repository.UserRepository;
import com.ciel.pocket.user.repository.ThemeRepository;
import com.ciel.pocket.user.service.AccountService;
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

        remoteResult = folderServiceClient.createDefault(remoteResult.getData());
        ReturnUtils.checkSuccess(remoteResult);

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

}
