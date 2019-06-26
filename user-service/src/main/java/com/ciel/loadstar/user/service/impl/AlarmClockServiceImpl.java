package com.ciel.loadstar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.infrastructure.exceptions.FriendlyException;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtil;
import com.ciel.loadstar.user.entity.AlarmClock;
import com.ciel.loadstar.user.repository.AlarmClockRepository;
import com.ciel.loadstar.user.service.AlarmClockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 16:00
 */
@Service
@Slf4j
public class AlarmClockServiceImpl extends ServiceImpl<AlarmClockRepository, AlarmClock> implements AlarmClockService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean save(AlarmClock entity) {
        Long now = System.currentTimeMillis();
        if (entity.getAlarmTime().getTime() - now <= 0){
            throw new FriendlyException("提醒时间不能小于当前时间");
        }

        boolean success = super.save(entity);
        String cacheKey = "NOTIFY:" + ApplicationContextUtil.getActiveProfile() + ":" + entity.getId();

        Long span = (entity.getAlarmTime().getTime() - now) / 1000;
        if (span > 0){
            redisTemplate.opsForValue().set(cacheKey, entity.getId().toString(), span, TimeUnit.SECONDS);
        }

        return success;
    }
}
