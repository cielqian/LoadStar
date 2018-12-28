package com.ciel.pocket.link.model;

import org.apache.ibatis.type.Alias;

import java.util.Date;
import javax.persistence.*;

@Table(name = "visit_record")
@Alias("VisitRecord")
public class VisitRecord {
    @Id
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "link_id")
    private Long linkId;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return is_delete
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public VisitRecord() {
        isDelete = false;
    }
}