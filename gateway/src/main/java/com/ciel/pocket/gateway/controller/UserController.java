package com.ciel.pocket.gateway.controller;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/3/29 15:57
 */
@RestController
public class UserController {
    @RequestMapping("/user")
    public String user(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return "123";
    }
}
