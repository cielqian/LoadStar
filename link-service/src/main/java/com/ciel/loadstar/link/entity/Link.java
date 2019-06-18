package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("link")
public class Link extends BaseEntity {

    public Link() {
        visitedCount = 0;
    }

    private String comment;

    private Long folderId;

    private String icon;

    private Date lastSeen;

    private String name;

    private Integer sortIndex;

    private String title;

    private String url;

    private Long userId;

    private Integer visitedCount;

    private String thumbnail;
}