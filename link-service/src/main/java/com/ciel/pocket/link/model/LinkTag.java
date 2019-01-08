package com.ciel.pocket.link.model;

import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.ibatis.type.Alias;

@Alias("LinkTag")
public class LinkTag {
    @TableId
    private Long linkId;

    @TableId
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