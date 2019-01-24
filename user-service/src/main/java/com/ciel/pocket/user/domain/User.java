package com.ciel.pocket.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    @TableLogic
    boolean isDelete;

    Long accountId;

    String username;

    String nickname;

    Date lastSeen;

    @TableField(fill = FieldFill.INSERT)
    Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    Date updateTime;

}
