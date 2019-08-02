package com.ciel.loadstar.user.mq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/8/1 16:38
 */
@Configuration
public class ProducerConfiguration {

    @Value("${loadstar.mq.host}")
    String mqHost;

    @Value("${spring.application.name}")
    String topicConsumerName;

    @Bean
    public DefaultMQProducer defaultProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(topicConsumerName);
        producer.setNamesrvAddr(mqHost);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(5);
        producer.start();
        return producer;
    }
}
