package com.ciel.pocket.user.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/15
 * @Comment
 */
@Data
public class User {
    @NotEmpty(message = "用户名不能为空")
    String username;

    @NotEmpty(message = "密码不能为空")
    String password;

    @NotEmpty(message = "用户昵称不能为空")
    String nickname;
}
