package com.ciel.pocket.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.ciel.pocket.infrastructure.enums.Language;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
@TableName("theme")
public class Theme {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    @TableLogic
    boolean is_delete;

    Long userId;

    ListTypeEnum listTypeEnum;

    Language language;

    String modules;

    String settings;

    String tips;

    @TableField(fill = FieldFill.INSERT)
    Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    Date updateTime;
}
