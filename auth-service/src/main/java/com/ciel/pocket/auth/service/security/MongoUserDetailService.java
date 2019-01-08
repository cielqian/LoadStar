package com.ciel.pocket.auth.service.security;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.auth.repository.UserRepository;
import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existing = userRepository.findUserByUsername(username);
        if (existing == null){
            throw new ObjectNotExistingException("账号或密码错误");
        }
        return existing;
    }
}
