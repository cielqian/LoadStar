package com.ciel.pocket.link.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "links")
@Data
public class Link {
    @Id
    String id;

    private String userId;

    private String url;

    private String icon;

    private String title;

    private String name;

    private String comment;

    private Date createTime;

    private Date lastSeen;

    private int visitedCount;
}
