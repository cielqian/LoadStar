package com.ciel.loadstar.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.*;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import com.ciel.loadstar.infrastructure.enums.Language;
import com.ciel.loadstar.user.infrastructure.enums.ListTypeEnum;
import lombok.Data;

import java.util.Date;

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
