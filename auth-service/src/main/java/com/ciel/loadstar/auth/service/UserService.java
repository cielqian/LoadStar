package com.ciel.loadstar.auth.service;

import com.ciel.loadstar.auth.domain.User;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/23
 * @Comment
 */
public interface UserService {
    User createUser(User user);

    void deleteUser(String userId);

    User queryUser(String username);
}
