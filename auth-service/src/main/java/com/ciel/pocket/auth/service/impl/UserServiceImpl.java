package com.ciel.pocket.auth.service.impl;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.auth.repository.UserRepository;
import com.ciel.pocket.auth.service.UserService;
import com.ciel.pocket.infrastructure.exceptions.ObjectExistingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/23
 * @Comment
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        User existing = queryUser(user.getUsername());
        if (existing != null)
            throw new ObjectExistingException("账号已存在");
        user.encodePassword();
        userRepository.insert(user);
        log.info("create user id " + user.getId());
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User queryUser(String username) {
        return userRepository.findUserByUsername(username);
    }


}
