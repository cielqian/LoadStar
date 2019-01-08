package com.ciel.pocket.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ciel.pocket.infrastructure.enums.Language;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import lombok.Data;

@Data
public class Theme {

    @JSONField(serialize = false)
    User user;

    Long userId;

    ListTypeEnum listTypeEnum;

    Language language;

    String modules;
}
