package com.ciel.pocket.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.dto.input.CreateUser;

public interface AccountService extends IService<User> {
    User queryById(Long id);

    User create(CreateUser user);

    void delete(Long userId);

    User findByName(String accountName);

    User findByAccountId(String accountId);
}
