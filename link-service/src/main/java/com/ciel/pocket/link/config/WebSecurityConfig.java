package com.ciel.pocket.link.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/9/19
 * @Comment
 */
//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .antMatchers("/api/users").permitAll()
                .and()
                .antMatcher("/swagger-ui.html").anonymous()
                .and()
                .antMatcher("/api/open/**").anonymous()
                .and()
                .csrf().disable();
    }
}
