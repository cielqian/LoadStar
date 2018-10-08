package com.ciel.pocket.user.service;

import com.ciel.pocket.user.domain.Account;
import com.ciel.pocket.user.domain.User;

import java.util.UUID;

public interface AccountService {
    Account queryById(Long id);

    Account create(User user);

    void delete(Long userId);

    Account findByName(String accountName);
}
