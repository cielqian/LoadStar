package com.ciel.pocket.auth.service.security;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.auth.repository.UserRepository;
import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existing = userRepository.findUserByUsername(username);
        if (existing == null){
            log.info("user not exist, username : "+ username);
            throw new ObjectNotExistingException("账号或密码错误");
        }
        return existing;
    }
}
