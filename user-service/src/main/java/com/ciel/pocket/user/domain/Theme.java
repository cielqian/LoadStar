package com.ciel.pocket.user.domain;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Theme extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    Account user;

    @Column(name = "userId", insertable = false, updatable = false)
    Long userId;

    ListTypeEnum listTypeEnum;
}
