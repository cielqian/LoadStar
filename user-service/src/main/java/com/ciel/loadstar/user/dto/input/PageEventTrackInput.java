package com.ciel.loadstar.user.dto.input;

import lombok.Data;

@Data
public class PageEventTrackInput {
    private String pageId;

    private Long eventTime;

    private String ctrlId;

    private String eventType;

}
