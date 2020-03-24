package com.ciel.loadstar.infrastructure.events.system;

import lombok.Data;

@Data
public class SystemEvent {
    private Long userId;

    private String profile;

    private String targetId;

    private String eventType;

    private Long eventTime;

    private String ext;

    public String toString(){
        // userId | targetId | eventTime | eventType | profile | ext
        return String.format("%s|%s|%s|%s|%s", userId, targetId, eventTime, eventType, profile, ext);
    }
}
