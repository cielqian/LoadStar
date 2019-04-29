package com.ciel.pocket.link.model;

public class LinkTag {

    private Long linkId;

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