package com.ciel.loadstar.user.controller;

import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.user.dto.input.CreateAlarmClock;
import com.ciel.loadstar.user.entity.AlarmClock;
import com.ciel.loadstar.user.service.AlarmClockService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 11:25
 */
@RestController
@RequestMapping("/clock")
@Slf4j
public class AlarmClockController {
    @Autowired
    AlarmClockService alarmClockService;

    @ApiOperation("创建提醒")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<AlarmClock> create(@RequestBody @Valid CreateAlarmClock alarmClockInput, @RequestHeader(Constants.Header_AccountId) Long userId){
        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setUserId(userId);
        alarmClock.setAlarmTime(alarmClockInput.getAlarmTime());
        alarmClock.setAlarmed(false);
        alarmClock.setComment(alarmClockInput.getComment());

        alarmClockService.save(alarmClock);

        log.info("create alarm clock success, id [{}]" , alarmClock.getId());
        return ApiReturnUtil.ok("创建成功",alarmClock);
    }
}
