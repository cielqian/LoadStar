package com.ciel.loadstar.infrastructure.events.web;

import com.ciel.loadstar.infrastructure.events.BaseEvent;

public class WebEvent extends BaseEvent {
    public WebEvent(){
    }

    public WebEvent(String eventType){
        setEventType(eventType);
    }
}
