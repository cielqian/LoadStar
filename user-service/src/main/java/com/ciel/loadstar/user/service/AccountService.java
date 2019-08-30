package com.ciel.loadstar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.user.dto.input.CreateUser;
import com.ciel.loadstar.user.entity.User;

public interface AccountService extends IService<User> {
    User queryById(Long id);

    User create(CreateUser user);

    void delete(Long userId);

    User findByName(String accountName);

    User findByAccountId(Long accountId);
}
