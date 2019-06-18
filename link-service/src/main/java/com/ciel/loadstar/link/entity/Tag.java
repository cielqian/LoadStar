package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tag")
public class Tag extends BaseEntity {
    private String code;

    private Boolean isSystem;

    private String name;

    private Integer sortIndex;

    private Long userId;
}