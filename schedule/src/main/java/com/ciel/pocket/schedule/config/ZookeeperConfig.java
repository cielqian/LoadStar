package com.ciel.pocket.schedule.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 17:25
 */
@Configuration
public class ZookeeperConfig {
    /**
     * 配置zookeeper
     * @param serverList
     * @param namespace
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${loadstar.elasticJob.zoo}") final String serverList,
            @Value("${loadstar.elasticJob.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}
