package com.ciel.loadstar.infrastructure.events;

import com.alibaba.fastjson.JSONObject;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtil;
import lombok.Data;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/4/26 15:40
 */

@Data
public class UserAccountEvent extends BaseEvent{
    public UserAccountEvent(){}

    public UserAccountEvent(String eventType){
        setEventType(eventType);
    }
}
