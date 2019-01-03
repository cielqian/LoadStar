package com.ciel.pocket.link.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Alias("Link")
public class Link {
    @Id
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @TableLogic
    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "update_time")
    private Date updateTime;

    private String comment;

    @Column(name = "folder_id")
    private Long folderId;

    private String icon;

    @Column(name = "last_seen")
    private Date lastSeen;

    private String name;

    @Column(name = "sort_index")
    private Integer sortIndex;

    private String title;

    private String url;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "visited_count")
    private Integer visitedCount;

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
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return folder_id
     */
    public Long getFolderId() {
        return folderId;
    }

    /**
     * @param folderId
     */
    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return last_seen
     */
    public Date getLastSeen() {
        return lastSeen;
    }

    /**
     * @param lastSeen
     */
    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return sort_index
     */
    public Integer getSortIndex() {
        return sortIndex;
    }

    /**
     * @param sortIndex
     */
    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * @return visited_count
     */
    public Integer getVisitedCount() {
        return visitedCount;
    }

    /**
     * @param visitedCount
     */
    public void setVisitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
    }
}