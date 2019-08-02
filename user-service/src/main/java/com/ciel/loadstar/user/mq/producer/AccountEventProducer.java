package com.ciel.loadstar.user.mq.producer;

import com.ciel.loadstar.infrastructure.events.account.AccountEvent;
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
public class AccountEventProducer {
    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Value("${loadstar.mq.topic.AccountEvent}")
    private String mqAccountEventTopic;

    public void send(AccountEvent accountEvent) {
        Message msg = null;
        try {
            msg = new Message(mqAccountEventTopic,accountEvent.getEventType(),
                    accountEvent.toJson().getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = defaultMQProducer.send(msg);
            log.info("send accountEvent success, event object id [{}], messageid [{}]",accountEvent.getId(), sendResult.getMsgId());
        } catch (Exception e) {
            log.info("send accountEvent fail, event object id [{}]", accountEvent.getId());
        }
    }
}
