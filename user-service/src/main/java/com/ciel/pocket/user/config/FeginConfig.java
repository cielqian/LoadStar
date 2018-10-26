package com.ciel.pocket.user.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/26 15:13
 */
@Configuration
public class FeginConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

}
