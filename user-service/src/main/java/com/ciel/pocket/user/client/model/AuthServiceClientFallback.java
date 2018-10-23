package com.ciel.pocket.user.client.model;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.client.AuthServiceClient;
import com.ciel.pocket.user.dto.input.CreateUser;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/23 14:49
 */
@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    @Override
    public ReturnModel<Long> createUser(CreateUser user) {
        return ReturnUtils.fail("");
    }

    @Override
    public ReturnModel deleteUser(String username) {
        return ReturnUtils.fail("");
    }
}
