package com.ciel.pocket.link.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ciel.pocket.infrastructure.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "folderId")
    @JSONField(serialize = false)
    private Folder folder;

    @Column(name = "folderId", insertable = false, updatable = false)
    Long folderId;

    @ManyToMany(mappedBy = "links")
    @JSONField(serialize = false)
    private Set<Tag> tags;
}
