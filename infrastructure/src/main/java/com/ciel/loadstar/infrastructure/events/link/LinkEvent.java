package com.ciel.loadstar.infrastructure.events.link;

import com.alibaba.fastjson.JSONObject;
import com.ciel.loadstar.infrastructure.events.BaseEvent;
import lombok.Data;

import java.util.Date;

/**
 * 书签事件
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 14:34
 */
@Data
public class LinkEvent extends BaseEvent {
    public LinkEvent(){
    }

    public LinkEvent(String eventType){
        setEventType(eventType);
    }
}
