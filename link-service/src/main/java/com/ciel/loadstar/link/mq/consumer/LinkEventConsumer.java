package com.ciel.loadstar.link.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ciel.loadstar.infrastructure.cache.CacheKeyFactory;
import com.ciel.loadstar.infrastructure.events.link.LinkEvent;
import com.ciel.loadstar.infrastructure.events.link.LinkEventType;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtil;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.es.ESRestClient;
import com.ciel.loadstar.link.es.model.ESLink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    @KafkaListener(topics = {"LinkEvent_Dev"})
    public void consume(ConsumerRecord<String, String> record) throws Exception {

        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()){
            String json = kafkaMessage.get();
            JSONObject jsonObject = JSONObject.parseObject(json);
            LinkEvent linkEvent = jsonObject.toJavaObject(LinkEvent.class);
            log.warn("received linkevent success, objId [{}]", linkEvent.getId());

            String profile = linkEvent.getProfile();
            String eventType = linkEvent.getEventType();
            Long linkId = Long.parseLong(linkEvent.getId());
            Link link = ((JSONObject)linkEvent.getObj()).toJavaObject(Link.class);

            if (StringUtils.equals(eventType, LinkEventType.CREATE)){
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
                    log.info("sync link [{}] to es success", linkId);

                } catch (IOException e) {
                    log.info("sync link [{}] to es fail", linkId);

                    e.printStackTrace();
                }
            }
            else if(StringUtils.equals(eventType, LinkEventType.DELETE)){
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
                    log.info("delete link [{}] from es success" ,linkId);
                }
                catch (IOException e) {
                    log.info("delete link [{}] from es fail" + linkId);
                    e.printStackTrace();
                }
            }
            else if(StringUtils.equals(eventType, LinkEventType.UPDATE)){
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
                            log.info("update link [{}] to es success",linkId);
                        }else{
                            IndexRequest request = new IndexRequest(index, document);
                            request.source(JSONObject.toJSONString(esLink), XContentType.JSON);
                            try {
                                client.index(request, RequestOptions.DEFAULT);
                                log.info("sync link [{}] to es success" , linkId);

                            } catch (IOException e) {
                                log.info("sync link [{}] to es fail" , linkId);

                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    log.info("update to es for link fail, id [{}]",linkId);
                }

            }
            else if(StringUtils.equals(eventType, LinkEventType.VIEW)){
                Map<String, String> cacheMap = new HashMap<>();
                cacheMap.put("service", "link");
                cacheMap.put("userid", link.getUserId().toString());
                cacheMap.put("date", DateFormatUtils.format(new Date(), "YYYY-MM-dd"));

                String cacheKey = CacheKeyFactory.build(CacheKeyFactory.LINK_VIEW_COUNT, cacheMap);
                if (!redisTemplate.hasKey(cacheKey)){
                    redisTemplate.opsForValue().increment(cacheKey, 1);
                    redisTemplate.expireAt(cacheKey, DateUtils.addDays(new Date(), 1));
                }else{
                    redisTemplate.opsForValue().increment(cacheKey, 1);
                }
            }
        }
    }

}
