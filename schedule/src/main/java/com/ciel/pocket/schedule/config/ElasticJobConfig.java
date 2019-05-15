package com.ciel.pocket.schedule.config;

import com.ciel.pocket.schedule.jobs.DeleteESIndexJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 17:23
 */
@Configuration
public class ElasticJobConfig {

    @Autowired
    ZookeeperRegistryCenter zookeeperRegistryCenter;

    /**
     * 配置任务详细信息
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                , jobClass.getCanonicalName())
        ).overwrite(true).build();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final DeleteESIndexJob simpleJob,
                                           @Value("${loadstar.elasticJob.jobs.deleteESIndex.cron}") final String cron,
                                           @Value("${loadstar.elasticJob.jobs.deleteESIndex.shardingTotalCount}") final int shardingTotalCount) {
        return new SpringJobScheduler(simpleJob, zookeeperRegistryCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, ""));
    }
}
