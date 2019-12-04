package com.ciel.loadstar.infrastructure.events.web;

import lombok.Data;

@Data
public class PageEventTrack {
    public PageEventTrack(){
    }

    private String eventId;

    private Long userId;

    private String profile;

    private String eventType;

    private Long eventTime;

    private String tag;

    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s", eventId, eventTime, userId, profile, eventType, tag);
    }
}