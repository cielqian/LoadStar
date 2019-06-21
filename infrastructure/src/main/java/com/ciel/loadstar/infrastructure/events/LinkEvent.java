package com.ciel.loadstar.infrastructure.events;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 14:34
 */
@Data
public class LinkEvent extends BaseEvent {
    public LinkEvent(String eventType){
        setEventType(eventType);
    }
}
