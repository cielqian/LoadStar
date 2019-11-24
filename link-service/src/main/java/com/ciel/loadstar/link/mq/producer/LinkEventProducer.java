package com.ciel.loadstar.link.mq.producer;

import com.ciel.loadstar.infrastructure.events.link.LinkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/8/1 16:35
 */
@Component
@Slf4j
public class LinkEventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(LinkEvent linkEvent) {
        Message msg = null;
        try {
            kafkaTemplate.send("LinkEvent_Dev", linkEvent.toJson());
            log.info("send linkevent success, event object id [{}],",linkEvent.getId());
        } catch (Exception e) {
            log.info("send linkevent fail, event object id [{}]", linkEvent.getId());
        }
    }
}
