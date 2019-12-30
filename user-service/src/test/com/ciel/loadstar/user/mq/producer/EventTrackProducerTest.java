package com.ciel.loadstar.user.mq.producer;

import com.ciel.loadstar.infrastructure.events.web.PageEventTrack;
import com.ciel.loadstar.user.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class EventTrackProducerTest {

    @Autowired
    EventTrackProducer eventTrackProducer;

    @Test
    public void send() {
        PageEventTrack pageEventTrack = new PageEventTrack();
        pageEventTrack.setEventType("click");
        pageEventTrack.setTag("page1");
        pageEventTrack.setUserId(1L);
        pageEventTrack.setProfile("dev");
        pageEventTrack.setEventTime(new Date().getTime());
        pageEventTrack.setEventId(UUID.randomUUID().toString());

        eventTrackProducer.send(pageEventTrack);
    }
}