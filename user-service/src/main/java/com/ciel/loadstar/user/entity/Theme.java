package com.ciel.loadstar.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import com.ciel.loadstar.infrastructure.enums.Language;
import com.ciel.loadstar.user.enums.ListTypeEnum;
import lombok.Data;

@Data
@TableName("theme")
public class Theme extends BaseEntity {

    Long userId;

    ListTypeEnum listTypeEnum;

    Language language;

    String modules;

    String settings;

    String tips;
}
