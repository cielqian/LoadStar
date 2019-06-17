package com.ciel.loadstar.infrastructure.events;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/4/26 15:40
 */

@Data
public class UserAccountEvent {
    String profile;

    String event;

    String id;

    Object obj;

    Long ts;

    public UserAccountEvent() {
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
