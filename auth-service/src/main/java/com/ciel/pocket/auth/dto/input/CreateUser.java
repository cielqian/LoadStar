package com.ciel.pocket.auth.dto.input;

import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/10 15:45
 */

@Data
public class CreateUser {
    String username;

    String password;
}
