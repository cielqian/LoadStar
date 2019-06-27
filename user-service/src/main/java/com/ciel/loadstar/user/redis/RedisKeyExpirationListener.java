package com.ciel.loadstar.user.redis;

import com.ciel.loadstar.user.entity.AlarmClock;
import com.ciel.loadstar.user.service.AlarmClockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtil;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 15:19
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Autowired
    AlarmClockService alarmClockService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        String prex = "NOTIFY:" + ApplicationContextUtil.getActiveProfile();

        if (expiredKey.startsWith(prex)){
            String[] values = expiredKey.split(":");
            Long id = Long.parseLong(values[2]);
            log.info("trigger alarm clock id [{}]", id);
            AlarmClock clock = alarmClockService.getById(id);
            clock.setAlarmed(true);
            alarmClockService.updateById(clock);
        }
    }
}
