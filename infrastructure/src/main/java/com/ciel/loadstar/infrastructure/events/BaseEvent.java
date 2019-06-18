package com.ciel.loadstar.infrastructure.events;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/18 17:16
 */
@Data
public abstract class BaseEvent {
    String profile;

    String event;

    String id;

    Object obj;

    Long ts;

    public BaseEvent() {
        ts = System.currentTimeMillis();
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("event", event);
        jsonObject.put("id", id);
        jsonObject.put("obj", obj);
        jsonObject.put("ts", ts);
        jsonObject.put("profile", profile);

        return jsonObject.toJSONString();
    }
}
