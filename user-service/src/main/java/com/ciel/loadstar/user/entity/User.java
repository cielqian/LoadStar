package com.ciel.loadstar.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User extends BaseEntity {

    Long accountId;

    String username;

    String nickname;

    Date lastSeen;

}
