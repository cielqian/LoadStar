package com.ciel.pocket.link.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/25 19:06
 */
@Component
public class KafkaConfig {

    @KafkaListener(topics = "Loadstar_Folder_Creater_Dev", groupId = "0")
    public void listen (ConsumerRecord<String, Long> record) throws Exception {
//        redisTemplate.opsForValue().set(record.key(), record.value(), 60, TimeUnit.SECONDS);
        System.out.printf("Loadstar_Folder_Creater_Dev Consumer : key = %s, value = %s \n", record.key(), record.value());
    }

//    @Bean(name = "kafkaListenerContainerFactory")
//    ConcurrentKafkaListenerContainerFactory<String, String>
//    kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    public ConsumerFactory<String, String> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//    }
//
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
//        return props;
//    }
}
