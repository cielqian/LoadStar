package com.ciel.pocket.link.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ciel.pocket.link.es.ESRestClient;
import com.ciel.pocket.link.es.model.ESLink;
import com.ciel.pocket.link.infrastructure.ApplicationContextUtils;
import com.ciel.pocket.link.model.Link;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/15 15:12
 */
@Component
@Log
public class LinkEventConsumer {
    @Autowired
    ESRestClient esRestClient;

    @KafkaListener(topics = "${loadstar.kafka.topic.LinkEvent}")
    public void listen (ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String profile = jsonObject.getString("profile");
        if (!StringUtils.equals(ApplicationContextUtils.getActiveProfile(), profile))
            return;

        String event = jsonObject.getString("event");
        if (StringUtils.equals(event, "NEW")){
            Long linkId = jsonObject.getLong("id");

            Link link = JSONObject.parseObject(jsonObject.getString("obj"), Link.class);

            RestHighLevelClient client = esRestClient.getClient();
            ESLink esLink = new ESLink();
            esLink.setName(link.getName());
            esLink.setTitle(link.getTitle());
            esLink.setTableId(link.getId());
            esLink.setUserId(link.getUserId());
            esLink.setProfile(ApplicationContextUtils.getActiveProfile());
            esLink.setCreatetime(link.getCreateTime());

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
    }
}
