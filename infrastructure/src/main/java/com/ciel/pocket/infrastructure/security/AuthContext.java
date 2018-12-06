package com.ciel.pocket.infrastructure.security;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.Map;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/12/6 17:25
 */

public class AuthContext {
    public final static UserDetail getUserDetail(Principal principal){
        OAuth2Authentication auth2Authentication = (OAuth2Authentication)principal;
        Map detail = ((Map)((Map)auth2Authentication.getUserAuthentication().getDetails()).get("principal"));
        Object id = detail.get("id");
        Object userName = detail.get("username");

        UserDetail userDetail = new UserDetail();
        userDetail.setAccountId(Long.parseLong(id.toString()));
        userDetail.setUserName(userName.toString());

        return userDetail;
    }
}
