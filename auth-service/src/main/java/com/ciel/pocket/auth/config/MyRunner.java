package com.ciel.pocket.auth.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/1/24 14:10
 */

@Component
public class MyRunner implements ApplicationRunner {

    @Value("${timeout}")
    String timeout;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String key = "timeout";
//        String defaultValue = "0";
//        Config config = ConfigService.getAppConfig();
//        String timeout = config.getProperty(key, defaultValue);

        System.out.println("timeout: "+ timeout);
    }
}
