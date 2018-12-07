package com.ciel.pocket.link.model;

import org.apache.ibatis.type.Alias;

import javax.persistence.*;

@Table(name = "link_tag")
@Alias("LinkTag")
public class LinkTag {
    @Id
    @Column(name = "link_id")
    private Long linkId;

    @Id
    @Column(name = "tag_id")
    private Long tagId;

    /**
     * @return link_id
     */
    public Long getLinkId() {
        return linkId;
    }

    /**
     * @param linkId
     */
    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    /**
     * @return tag_id
     */
    public Long getTagId() {
        return tagId;
    }

    /**
     * @param tagId
     */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}