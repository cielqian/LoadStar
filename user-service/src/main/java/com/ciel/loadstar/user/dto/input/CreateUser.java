package com.ciel.loadstar.user.dto.input;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 19:05
 */
@Data
public class CreateUser {
    @NotEmpty(message = "用户名不能为空")
    String username;

    @NotEmpty(message = "密码不能为空")
    String password;

    @NotEmpty(message = "用户昵称不能为空")
    String nickname;
}
