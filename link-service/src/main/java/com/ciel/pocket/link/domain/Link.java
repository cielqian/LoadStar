package com.ciel.pocket.link.domain;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class Link extends BaseEntity {

    private Long userId;

    private String url;

    private String icon;

    private String title;

    private String name;

    private String comment;

    private Integer sortIndex;

    private Date lastSeen;

    private int visitedCount;
}
