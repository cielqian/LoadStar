package com.ciel.loadstar.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.events.system.SystemEvent;
import com.ciel.loadstar.infrastructure.events.web.PageEventTrack;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.user.dto.input.PageEventTrackInput;
import com.ciel.loadstar.user.service.EventTrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.management.snmp.jvminstr.JvmOSImpl;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/track")
public class TrackController {

    @Autowired
    EventTrackService eventTrackService;

    /**
     * 事件埋点
     * @param content userid,eventType,pageid,timestamp
     * @return
     */
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ReturnModel event(String content){

        return ApiReturnUtil.ok("提交成功");
    }

    /**
     * 页面埋点
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ReturnModel page(@RequestBody PageEventTrackInput trackInput){
        Long accountId = SessionResourceUtil.getCurrentAccountId();

        PageEventTrack pageEventTrack = new PageEventTrack();
        pageEventTrack.setEventId(UUID.randomUUID().toString());
        pageEventTrack.setUserId(accountId);
        pageEventTrack.setEventTime(trackInput.getEventTime());
        pageEventTrack.setEventType(trackInput.getEventType());
        pageEventTrack.setTag(trackInput.getCtrlId());
        eventTrackService.track(pageEventTrack);

        JSONObject pageEvent = new JSONObject();
        pageEvent.put("page", trackInput.getPageId());
        pageEvent.put("ctrl", trackInput.getCtrlId());

        SystemEvent event = new SystemEvent();
        event.setUserId(accountId);
        event.setEventTime(trackInput.getEventTime());
        event.setEventType(trackInput.getEventType());
        event.setTargetId("");
        event.setExt(pageEvent.toJSONString());
        eventTrackService.track(event);

        return ApiReturnUtil.ok("提交成功");
    }
}
