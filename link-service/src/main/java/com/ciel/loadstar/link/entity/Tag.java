package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tag")
public class Tag extends BaseEntity {
    private String code;

    private Boolean isSystem;

    private String name;

    private Integer sortIndex;

    private Long userId;
}