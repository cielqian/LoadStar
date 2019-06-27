package com.ciel.loadstar.user.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 11:25
 */
@RestController
@RequestMapping("/api/clock")
@Slf4j
public class AlarmClockController {
    @Autowired
    AlarmClockService alarmClockService;

    @ApiOperation("创建提醒")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<Long> create(@RequestBody @Valid CreateAlarmClock alarmClockInput, @RequestHeader(Constants.Header_AccountId) Long userId){
        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setUserId(userId);
        alarmClock.setAlarmTime(alarmClockInput.getAlarmTime());
        alarmClock.setAlarmed(false);
        alarmClock.setComment(alarmClockInput.getComment());

        alarmClockService.save(alarmClock);

        log.info("create alarm clock success, id [{}]" , alarmClock.getId());
        return ApiReturnUtil.ok("创建成功",alarmClock.getId());
    }

    @ApiOperation("删除提醒")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ReturnModel<AlarmClock> delete(@PathVariable("id") Long clockId){
        alarmClockService.removeById(clockId);

        log.info("remove alarm clock success, id [{}]" , clockId);
        return ApiReturnUtil.ok("删除成功");
    }

    @ApiOperation("查询提醒")
    @RequestMapping(method = RequestMethod.GET)
    public ReturnModel<List<AlarmClock>> query(@RequestHeader(Constants.Header_AccountId) Long userId){
        QueryWrapper<AlarmClock> qw = new QueryWrapper<AlarmClock>();
        qw.eq("user_id", userId);
        qw.eq("is_alarmed", false);
        List<AlarmClock> alarmClocks = alarmClockService.list(qw);
        return ApiReturnUtil.ok("查询成功", alarmClocks);
    }
}
