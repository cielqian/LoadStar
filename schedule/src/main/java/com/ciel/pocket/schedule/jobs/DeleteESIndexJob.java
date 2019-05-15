package com.ciel.pocket.schedule.jobs;

import com.ciel.pocket.schedule.es.ESRestClient;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.java.Log;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 16:55
 */
@Component
@Log
public class DeleteESIndexJob implements SimpleJob {

    @Autowired
    ESRestClient esRestClient;

    @Override
    public void execute(ShardingContext shardingContext) {
        RestHighLevelClient client = esRestClient.getClient();

        GetIndexRequest getIndexRequest = new GetIndexRequest();

        Date now = new Date();
        for (int i = 2; i <= 4; i++) {
            String day = DateFormatUtils.format(DateUtils.addDays(now, -i), "yyyy.MM.dd");
            String indexName = "loadstar_logstash_index_" + day;
            getIndexRequest.indices(indexName);

            try {
                boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
                if (exists){
                    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("loadstar_logstash_index_2019.05.15");
                    AcknowledgedResponse acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                    log.info("delete index " + indexName);
                    continue;
                }
                log.info("unfound index " + indexName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
