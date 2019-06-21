package com.ciel.loadstar.link.mq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/21 17:17
 */

@Data
@RefreshScope
public class LoadstarTopic {

    @Value("${loadstar.kafka.topic.UserAccountEvent}")
    private String UserAccountEventTopic;

    @Value("${loadstar.kafka.topic.LinkEvent}")
    private String LinkEventTopic;
}
