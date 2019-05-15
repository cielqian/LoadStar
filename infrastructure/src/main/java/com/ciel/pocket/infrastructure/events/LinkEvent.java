package com.ciel.pocket.infrastructure.events;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 14:34
 */
@Data
public class LinkEvent {
    String profile;

    String event;

    String id;

    Object obj;

    Long ts;

    public LinkEvent() {
        ts = new Date().getTime();
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
