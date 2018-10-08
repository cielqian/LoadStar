package com.ciel.pocket.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ciel.pocket.infrastructure.domain.BaseEntity;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Theme extends BaseEntity {

    @JSONField(serialize = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;

    @Column(name = "userId", insertable = false, updatable = false)
    Long userId;

    ListTypeEnum listTypeEnum;
}
