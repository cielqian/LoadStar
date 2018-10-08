package com.ciel.pocket.link.infrastructure.utils;

import com.ciel.pocket.link.domain.UserDetail;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class AuthContext {
    public final static UserDetail getUserDetail(Principal principal){
        OAuth2Authentication auth2Authentication = (OAuth2Authentication)principal;
        Map detail = ((Map)((Map)auth2Authentication.getUserAuthentication().getDetails()).get("principal"));
        Object id = detail.get("id");
        Object userName = detail.get("username");

        UserDetail userDetail = new UserDetail();
        userDetail.setId(id.toString());
        userDetail.setUserName(userName.toString());

        return userDetail;
    }
}
