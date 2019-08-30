package com.ciel.loadstar.infrastructure.events.account;

import com.ciel.loadstar.infrastructure.events.BaseEvent;
import lombok.Data;

/**
 * 账户事件
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/8/2 10:38
 */
@Data
public class AccountEvent extends BaseEvent {
    public AccountEvent(){
    }

    public AccountEvent(String eventType){
        setEventType(eventType);
    }
}