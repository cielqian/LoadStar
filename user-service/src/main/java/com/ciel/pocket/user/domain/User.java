package com.ciel.pocket.user.domain;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class User extends BaseEntity {

    String accountId;

    String username;

    String nickname;

    Date lastSeen;

    @OneToMany(mappedBy = "user")
    List<Theme> themes;
}
