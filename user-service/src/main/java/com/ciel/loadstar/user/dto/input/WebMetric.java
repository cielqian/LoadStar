package com.ciel.loadstar.user.dto.input;

import lombok.Data;

@Data
public class WebMetric {
    private String pageId;

    private Long eventTime;

    private String ctrlId;

    private String eventType;

}
