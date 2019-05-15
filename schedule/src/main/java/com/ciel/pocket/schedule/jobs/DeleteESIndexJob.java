package com.ciel.pocket.schedule.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 16:55
 */

public class DeleteESIndexJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("1");
    }
}
