package com.ciel.pocket.link.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long userId;

    private String url;

    private String icon;

    private String title;

    private String name;

    private String comment;

    private Date createTime;

    private Date lastSeen;

    private int visitedCount;
}
