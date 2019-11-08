package com.ciel.loadstar.link.mq.producer;

import com.ciel.loadstar.infrastructure.events.link.LinkEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/8/1 16:35
 */
@Component
@Slf4j
public class LinkEventProducer {
//    @Autowired
//    DefaultMQProducer defaultMQProducer;
//
//    @Value("${loadstar.mq.topic.LinkEvent}")
//    private String mqLinkEventTopic;
//
//    public void send(LinkEvent linkEvent) {
//        Message msg = null;
//        try {
//            msg = new Message(mqLinkEventTopic,linkEvent.getEventType(),
//                    linkEvent.toJson().getBytes(RemotingHelper.DEFAULT_CHARSET));
//            SendResult sendResult = defaultMQProducer.send(msg);
//            log.info("send linkevent success, event object id [{}], messageid [{}]",linkEvent.getId(), sendResult.getMsgId());
//        } catch (Exception e) {
//            log.info("send linkevent fail, event object id [{}]", linkEvent.getId());
//        }
//    }
}
