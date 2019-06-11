package com.ciel.pocket.link.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ciel.pocket.link.es.ESRestClient;
import com.ciel.pocket.link.es.model.ESLink;
import com.ciel.pocket.link.infrastructure.ApplicationContextUtils;
import com.ciel.pocket.link.mapper.FolderMapper;
import com.ciel.pocket.link.model.Folder;
import com.ciel.pocket.link.model.Link;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 15:12
 */
@Component
@Slf4j
public class LinkEventConsumer {
    @Autowired
    ESRestClient esRestClient;

    @Autowired
    CacheManager cacheManager;

    @KafkaListener(topics = "${loadstar.kafka.topic.LinkEvent}")
    public void listen (ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String profile = jsonObject.getString("profile");
        if (!StringUtils.equals(ApplicationContextUtils.getActiveProfile(), profile))
            return;

        String event = jsonObject.getString("event");
        Long linkId = jsonObject.getLong("id");
        Link link = JSONObject.parseObject(jsonObject.getString("obj"), Link.class);

        if (StringUtils.equals(event, "NEW")){
            RestHighLevelClient client = esRestClient.getClient();
            ESLink esLink = new ESLink();
            esLink.setName(link.getName());
            esLink.setTitle(link.getTitle());
            esLink.setTableId(link.getId());
            esLink.setUserId(link.getUserId());
            esLink.setProfile(ApplicationContextUtils.getActiveProfile());
            esLink.setCreatetime(link.getCreateTime());
            esLink.setUrl(link.getUrl());

            IndexRequest request = new IndexRequest("loadstar", "links");
            request.source(JSONObject.toJSONString(esLink), XContentType.JSON);

            try {
                IndexResponse response = client.index(request);
                log.info("sync to es for link success, id : " + linkId);

            } catch (IOException e) {
                log.info("sync to es for link fail, id : " + linkId);

                e.printStackTrace();
            }

        }
        else if(StringUtils.equals(event, "DELETE")){
            DeleteByQueryRequest request = new DeleteByQueryRequest("loadstar");
            request.setConflicts("proceed");

            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            TermQueryBuilder tableIdTermQuery = QueryBuilders.termQuery("tableId", linkId);
            TermQueryBuilder accountIdTermQuery = QueryBuilders.termQuery("userId", link.getUserId());
            boolQueryBuilder.must(tableIdTermQuery)
                    .must(accountIdTermQuery);
            request.setQuery(boolQueryBuilder);

            try {
                RestHighLevelClient client = esRestClient.getClient();
                BulkByScrollResponse response =
                        client.deleteByQuery(request, RequestOptions.DEFAULT);
                log.info("delete from es for link success, id [{}]" ,linkId);
            }
            catch (IOException e) {
                log.info("delete from es for link fail, id [{}] " + linkId);
                e.printStackTrace();
            }
        }
    }
}
