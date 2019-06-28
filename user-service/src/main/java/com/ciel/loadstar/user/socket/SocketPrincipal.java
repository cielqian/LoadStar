package com.ciel.loadstar.user.socket;

import lombok.Data;

import java.security.Principal;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/28 11:48
 */

public class SocketPrincipal implements Principal {
    String username;

    public SocketPrincipal(String username){
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
}
