package com.ciel.pocket.user.service;

import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.dto.input.CreateUser;

public interface AccountService {
    User queryById(Long id);

    User create(CreateUser user);

    void delete(Long userId);

    User findByName(String accountName);
}
