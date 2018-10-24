package com.ciel.pocket.turbinedashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/24 14:56
 */

@SpringBootApplication
@EnableHystrixDashboard
public class TurbineDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(TurbineDashboardApplication.class, args);
    }
}
