package com.ciel.loadstar.user.dto.input;

import lombok.Data;

@Data
public class PageEventTrackInput {
    /**
     * 页面ID
     */
    private String pageId;

    /**
     * 事件时间
     */
    private Long eventTime;

    /**
     * 控件ID
     */
    private String ctrlId;

    /**
     * 事件类型
     */
    private String eventType;

}
