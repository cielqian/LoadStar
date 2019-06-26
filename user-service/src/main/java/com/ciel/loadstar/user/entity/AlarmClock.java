package com.ciel.loadstar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 15:49
 */

@Data
@TableName("alarmclock")
public class AlarmClock extends BaseEntity {
    Long userId;

    Date alarmTime;

    String comment;

    boolean isAlarmed;
}
