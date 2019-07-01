package com.ciel.loadstar.user.dto.input;

import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 16:03
 */
@Data
public class CreateAlarmClock {
    Date alarmTime;

    String comment;
}
