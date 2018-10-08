package com.ciel.pocket.auth.service.security;

import com.ciel.pocket.auth.repository.UserRepository1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository1 userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() ->new UsernameNotFoundException(username));
    }
}
