package com.ciel.loadstar.user.service.impl;

import com.ciel.loadstar.infrastructure.events.web.PageEventTrack;
import com.ciel.loadstar.user.mq.producer.EventTrackProducer;
import com.ciel.loadstar.user.service.EventTrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventTrackServiceImpl implements EventTrackService {
    @Autowired
    EventTrackProducer eventTrackProducer;

    @Override
    public void track(PageEventTrack pageEventTrack) {
        eventTrackProducer.send(pageEventTrack);
    }
}
