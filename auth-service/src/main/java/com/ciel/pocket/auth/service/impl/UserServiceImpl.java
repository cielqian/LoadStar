package com.ciel.pocket.auth.service.impl;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.auth.repository.UserRepository;
import com.ciel.pocket.auth.service.UserService;
import com.ciel.pocket.infrastructure.exceptions.ObjectExistingException;
import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/23
 * @Comment
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        Optional<User> existing = queryUser(user.getUsername());
        if (existing.isPresent())
            throw new ObjectExistingException("账号已存在");
        user.encodePassword();
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.delete(queryUser(username).orElseThrow(() -> new ObjectNotExistingException("账号不存在")));
    }

    @Override
    public Optional<User> queryUser(String username) {
        return userRepository.findUserByUsername(username);
    }


}
