package com.ciel.loadstar.user.dto.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 17:46
 */
@Data
@ApiModel("用户信息")
public class UserInfo {

    @ApiModelProperty(name = "用户Id")
    private Long userId;

    @ApiModelProperty(name = "账号Id")
    private Long accountId;

    @ApiModelProperty(name = "用户账号")
    private String username;

    @ApiModelProperty(name = "用户昵称")
    private String nickname;

    @ApiModelProperty(name = "最后登录时间")
    private Date lastSeen;
}
