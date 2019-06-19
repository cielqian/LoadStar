package com.ciel.loadstar.link.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtil;
import com.ciel.loadstar.link.es.model.ESLink;
import com.ciel.loadstar.link.es.ESRestClient;
import com.ciel.loadstar.link.entity.Link;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${loadstar.es.index:loadstar}")
    String index;

    @Value("${loadstar.es.doc.link:links}")
    String document;

    @KafkaListener(topics = "${loadstar.kafka.topic.LinkEvent}")
    public void listen (ConsumerRecord<String, String> record) throws Exception {
        String json = record.value();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String profile = jsonObject.getString("profile");
        if (!StringUtils.equals(ApplicationContextUtil.getActiveProfile(), profile))
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
            esLink.setProfile(ApplicationContextUtil.getActiveProfile());
            esLink.setCreatetime(link.getCreateTime());
            esLink.setUrl(link.getUrl());

            IndexRequest request = new IndexRequest(index, document);
            request.source(JSONObject.toJSONString(esLink), XContentType.JSON);

            try {
                client.index(request, RequestOptions.DEFAULT);
                log.info("sync to es for link success, id : " + linkId);

            } catch (IOException e) {
                log.info("sync to es for link fail, id : " + linkId);

                e.printStackTrace();
            }
        }
        else if(StringUtils.equals(event, "DELETE")){
            DeleteByQueryRequest request = new DeleteByQueryRequest(index);
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
        else if(StringUtils.equals(event, "UPDATE")){
            RestHighLevelClient client = esRestClient.getClient();
            ESLink esLink = new ESLink();
            esLink.setName(link.getName());
            esLink.setTitle(link.getTitle());
            esLink.setTableId(link.getId());
            esLink.setUserId(link.getUserId());
            esLink.setProfile(ApplicationContextUtil.getActiveProfile());
            esLink.setCreatetime(link.getCreateTime());
            esLink.setUrl(link.getUrl());


            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            TermQueryBuilder tableIdTreamQuery = QueryBuilders.termQuery("tableId", linkId);
            boolQueryBuilder.must(tableIdTreamQuery);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(1);
            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.types(document);
            searchRequest.source(searchSourceBuilder);
            try {
                SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                if (searchResponse.status() == RestStatus.OK){
                    SearchHits hits = searchResponse.getHits();
                    SearchHit[] searchHits = hits.getHits();
                    if (searchHits.length == 1){
                        SearchHit hit = searchHits[0];
                        String esId = hit.getId();

                        UpdateRequest updateRequest = new UpdateRequest(index, document, esId);
                        updateRequest.doc(JSONObject.toJSONString(esLink), XContentType.JSON);
                        client.update(updateRequest, RequestOptions.DEFAULT);
                        log.info("update to es for link success, id [{}]",linkId);
                    }else{
                        IndexRequest request = new IndexRequest(index, document);
                        request.source(JSONObject.toJSONString(esLink), XContentType.JSON);
                        try {
                            client.index(request, RequestOptions.DEFAULT);
                            log.info("sync to es for link success, id : " + linkId);

                        } catch (IOException e) {
                            log.info("sync to es for link fail, id : " + linkId);

                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                log.info("update to es for link fail, id [{}]",linkId);

                e.printStackTrace();
            }

        }
    }
}
