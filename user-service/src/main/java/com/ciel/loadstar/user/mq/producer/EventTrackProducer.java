package com.ciel.loadstar.user.mq.producer;

import com.ciel.loadstar.infrastructure.events.system.SystemEvent;
import com.ciel.loadstar.infrastructure.events.web.PageEventTrack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventTrackProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(PageEventTrack pageEventTrack) {
        Message msg = null;
        try {
            kafkaTemplate.send("EventTrack", pageEventTrack.toString());
            log.info("send linkevent success, event object id [{}]", pageEventTrack.getEventId());
        } catch (Exception e) {
            log.info("send linkevent fail, event object id [{}]", pageEventTrack.getEventId());
        }
    }

    public void send(SystemEvent event) {
        Message msg = null;
        try {
            kafkaTemplate.send("SystemEvent", event.toString());
            log.debug("send event success");
        } catch (Exception e) {
            log.info("send event fail [{}]", event.toString());
        }
    }
}
