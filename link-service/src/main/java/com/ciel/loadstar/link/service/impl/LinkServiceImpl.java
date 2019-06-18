package com.ciel.loadstar.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.loadstar.infrastructure.events.LinkEvent;
import com.ciel.loadstar.infrastructure.utils.ApplicationContextUtils;
import com.ciel.loadstar.link.dto.input.AnalysisLinkInput;
import com.ciel.loadstar.link.dto.input.QueryLinkListInput;
import com.ciel.loadstar.link.dto.output.AnalysisLinkOutput;
import com.ciel.loadstar.link.dto.output.PageableListModel;
import com.ciel.loadstar.link.es.ESRestClient;
import com.ciel.loadstar.link.entity.Folder;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.entity.LinkTag;
import com.ciel.loadstar.link.entity.VisitRecord;
import com.ciel.loadstar.link.repository.FolderRepository;
import com.ciel.loadstar.link.repository.LinkRepository;
import com.ciel.loadstar.link.repository.LinkTagRepository;
import com.ciel.loadstar.link.repository.VisitRecordRepository;
import com.ciel.loadstar.link.service.LinkService;
import com.ciel.loadstar.link.service.linkParser.JsoupLinkParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LinkServiceImpl extends ServiceImpl<LinkRepository, Link> implements LinkService {

    @Autowired
    LinkTagRepository linkTagMapper;

    @Autowired
    FolderRepository folderMapper;

    @Autowired
    VisitRecordRepository visitRecordMapper;

    @Autowired
    JsoupLinkParser linkParser;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${loadstar.kafka.topic.LinkEvent}")
    private String linkTopic;

    @Autowired
    private ESRestClient esRestClient;

    @Autowired
    CacheManager cacheManager;

    @Override
    public Long create(Link link, List<Long> tags) {
        Integer totalCount = baseMapper.countByUser(link.getUserId());

        Date now = new Date();

        link.setIsDelete(false);
        link.setLastSeen(now);
        link.setVisitedCount(0);
        link.setSortIndex(totalCount + 1);
        link.setCreateTime(now);

        if (link.getFolderId() == null || link.getFolderId() == 0){
            Folder folder = folderMapper.queryFolderByCode(link.getUserId(), "default");
            if (folder != null){
                link.setFolderId(folder.getId());
            }
        }

        baseMapper.insert(link);

        LinkEvent event = new LinkEvent();
        event.setEvent("NEW");
        event.setId(link.getId().toString());
        event.setProfile(ApplicationContextUtils.getActiveProfile());
        event.setObj(link);
        String jsonString = event.toJson();
        ListenableFuture future = kafkaTemplate.send(linkTopic, jsonString);
        future.addCallback(o -> log.info("send to topic LinkEvent success:" + jsonString)
                , throwable -> log.info("send to topic LinkEvent fail:" + jsonString));

//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("loadstar-6473901552.us-west-2.bonsaisearch.net", 80, "http"))
//                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder ) {
//                        return httpClientBuilder
//                                .setDefaultCredentialsProvider(credentialsProvider);
//                    }
//                })
//                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//                    @Override
//                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
//                        return builder.setConnectTimeout(5000).setSocketTimeout(60000);
//                    }
//                })
//                .setMaxRetryTimeoutMillis(60000)
//        );



        if (tags != null){
            tags.forEach(tagId -> {
                LinkTag linkTag = new LinkTag();
                linkTag.setLinkId(link.getId());
                linkTag.setTagId(tagId);
                linkTagMapper.insert(linkTag);
            });
        }

        //生成网站缩略图
//        ListenableFuture future = kafkaTemplate.send(linkThumbnailTopic, link.getId().toString());
//        future.addCallback(o -> System.out.println("send to linkThumbnailTopic success:" + link.getId())
//                , throwable -> System.out.println("send to linkThumbnailTopic fail:" + link.getId()));

        return link.getId();
    }

    @Override
    public Long update(Link link, List<Long> tags) {
        baseMapper.updateById(link);
        QueryWrapper<LinkTag> qw = new QueryWrapper<LinkTag>();
        qw.eq("link_id", link.getId());
        qw.notIn("tag_id", "-1");
        linkTagMapper.delete(qw);

        if (tags != null){
            tags.forEach(tagId -> {
                LinkTag linkTag = new LinkTag();
                linkTag.setLinkId(link.getId());
                linkTag.setTagId(tagId);
                linkTagMapper.insert(linkTag);
            });
        }
        LinkEvent event = new LinkEvent();
        event.setEvent("UPDATE");
        event.setId(link.getId().toString());
        event.setProfile(ApplicationContextUtils.getActiveProfile());
        event.setObj(link);
        String jsonString = event.toJson();
        ListenableFuture future = kafkaTemplate.send(linkTopic, jsonString);
        future.addCallback(o -> log.info("send to topic LinkEvent success link: [{}]", jsonString)
                , throwable -> log.info("send to topic LinkEvent fail link: [{}]", jsonString));

        String cacheKey = "f:" + link.getFolderId() + ":u:" + link.getUserId();
        cacheManager.getCache("links").evict(cacheKey);
        log.info("evict cache key[{}]", cacheKey);
        log.info("update link success id [{}]", link.getId());
        return link.getId();
    }

    @Override
    public void delete(Long linkId) {
        Link link = query(linkId);
        Assert.notNull(link, "链接不存在");

        cacheManager.getCache("links").evict("f:" + link.getFolderId() + ":u:" + link.getUserId());
        baseMapper.deleteById(linkId);
        //linkRepository.updateSortIndexBatch(link.getUserId(), link.getSortIndex());
    }

    @Override
    public void trash(Long linkId, Long accountId) {
        Link link = baseMapper.selectById(linkId);
        Folder folder = folderMapper.queryFolderByCode(accountId, "trash");
        if (folder != null){
            baseMapper.updateFolderById(linkId, folder.getId());
        }
        cacheManager.getCache("links").evict("f:" + link.getFolderId() + ":u:" + link.getUserId());
        cacheManager.getCache("links").evict("f:" + folder.getId() + ":u:" + link.getUserId());

        LinkEvent event = new LinkEvent();
        event.setEvent("DELETE");
        event.setId(link.getId().toString());
        event.setProfile(ApplicationContextUtils.getActiveProfile());
        event.setObj(link);
        String jsonString = event.toJson();
        ListenableFuture future = kafkaTemplate.send(linkTopic, jsonString);
        future.addCallback(o -> log.info("send to topic LinkEvent success:" + jsonString)
                , throwable -> log.info("send to topic LinkEvent fail:" + jsonString));
    }

    @Override
    public void move(Long linkId, Long folderId) {
        baseMapper.updateFolderById(linkId, folderId);
    }

    @Override
    public void visit(Long linkId) {
        Link link = baseMapper.selectById(linkId);
        Assert.notNull(link, "链接不存在");

        link.setLastSeen(new Date());
        link.setVisitedCount(link.getVisitedCount()+1);

        baseMapper.updateById(link);

        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setLinkId(linkId);
        visitRecord.setUserId(link.getUserId());
        visitRecordMapper.insert(visitRecord);

    }

    @Override
    public void up(Long linkId) {
//        Link link = query(linkId);
//        if (link.getSortIndex() == 1){
//            return;
//        }
//
//        Link preLink = linkRepository.findByUserIdEqualsAndSortIndexEquals(link.getUserId(), link.getSortIndex() - 1);
//        if (preLink == null){
//            return;
//        }
//        link.setSortIndex(link.getSortIndex() - 1);
//        preLink.setSortIndex(preLink.getSortIndex() + 1);
//
//        linkRepository.save(link);
//        linkRepository.save(preLink);

    }

    @Override
    public void down(Long linkId) {
//        Link link = query(linkId);
//
//        Link nextLink = linkRepository.findByUserIdEqualsAndSortIndexEquals(link.getUserId(), link.getSortIndex() + 1);
//        if (nextLink == null){
//            return;
//        }
//        link.setSortIndex(link.getSortIndex() + 1);
//        nextLink.setSortIndex(nextLink.getSortIndex() - 1);
//
//        linkRepository.save(link);
//        linkRepository.save(nextLink);
    }

    @Override
    public Link query(Long linkId) {
        Link link = baseMapper.selectById(linkId);
        Assert.notNull(link, "链接不存在");

        return link;
    }

    @Override
    public PageableListModel<Link> queryPageList(Long accountId, QueryLinkListInput queryInput) {
        PageableListModel<Link> linkPageableListModel = new PageableListModel<>();
        Page<Link> page = new Page<Link>();
        page.setSize(queryInput.getPageSize());
        page.setPages(queryInput.getCurrentPage());

        QueryWrapper<Link> qw = new QueryWrapper<Link>();
        qw.eq("is_delete", "0");
        qw.eq("user_id", accountId);
        if (StringUtils.isNotBlank(queryInput.getKeyword())){
            qw.and(l -> l.like("name", queryInput.getKeyword())
                    .or().like("title", queryInput.getKeyword()));
        }

        IPage links = baseMapper.selectPage(page, qw);
        linkPageableListModel.setItems(links.getRecords());
        linkPageableListModel.setTotal(links.getTotal());

        return linkPageableListModel;
    }

    @Override
    public PageableListModel<Link> fullTextSearch(Long accountId, QueryLinkListInput queryInput) {
        PageableListModel<Link> result = new PageableListModel<>();
        result.setItems(new ArrayList<>());

        RestHighLevelClient client = esRestClient.getClient();

        SearchRequest searchRequest = new SearchRequest("loadstar");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        MatchQueryBuilder titleMatchQueryBuilder = QueryBuilders.matchQuery("title", queryInput.getKeyword());
        TermQueryBuilder accountIdTermQueryBuilder = QueryBuilders.termQuery("userId", accountId);
        TermQueryBuilder profileTermQueryBuilder = QueryBuilders.termQuery("profile", ApplicationContextUtils.getActiveProfile());

        boolQueryBuilder.must(titleMatchQueryBuilder)
                .must(accountIdTermQueryBuilder)
                .must(profileTermQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightTitle.preTags("<em>");
        highlightTitle.postTags("</em>");
        highlightBuilder.field(highlightTitle);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.types("links");
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.status() == RestStatus.OK){
                SearchHits hits = searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    Link link = new Link();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    link.setId(Long.parseLong(sourceAsMap.get("tableId").toString()));
                    link.setUrl((String) sourceAsMap.get("url"));
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField highlight = highlightFields.get("title");
                    link.setTitle(highlight.fragments()[0].string());

                    result.getItems().add(link);
                }
                result.setTotal(hits.totalHits);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Link> queryTop5List(Long accountId) {
//        PageHelper.startPage(1, 5);
        Page<Link> page = new Page<>(1, 5);

//        Sort sort = new Sort(Sort.Direction.DESC, "visitedCount");
//        Pageable pageable = PageRequest.of(0, 5, sort);
        return baseMapper.queryAll(page, null);
    }

    @Override
    public List<Link> queryRecent5List(Long accountId) {
//        PageHelper.startPage(1, 5);
        Page<Link> page = new Page<>(1, 5);

        return baseMapper.queryAll(page, null);
    }

    @Override
    public List<Link> queryLinksUnderFolder(Long accountId, Long folderId) {
        return baseMapper.queryAllUnderFolder(accountId, folderId);
    }

    @Override
    public void deleteLinksUnderFolder(Long accountId,Long folderId) {
        baseMapper.deleteByFolder(folderId);

        cacheManager.getCache("links").evict("f:" + folderId+ ":u:" + accountId);

        Link link = new Link();
        link.setId(-1L);
        link.setFolderId(folderId);
        link.setUserId(accountId);
        LinkEvent event = new LinkEvent();
        event.setEvent("DELETE");
        event.setId(link.getId().toString());
        event.setProfile(ApplicationContextUtils.getActiveProfile());
        event.setObj(link);
        String jsonString = event.toJson();
        ListenableFuture future = kafkaTemplate.send(linkTopic, jsonString);
        future.addCallback(o -> log.info("send to topic LinkEvent success:" + jsonString)
                , throwable -> log.info("send to topic LinkEvent fail:" + jsonString));
    }

    @Override
    public List<Link> queryLinksUnderTag(Long accountId, Long tagId) {
        return baseMapper.queryAllUnderTag(accountId, tagId);
    }

    @Override
    public AnalysisLinkOutput analysis(AnalysisLinkInput input) {
        return linkParser.analysis(input.getUrl());
    }

    @Override
    public void addLinkToTag(Long linkId, Long tagId) {
        QueryWrapper<LinkTag> wrapper = new QueryWrapper<>();
        wrapper.eq("link_id", linkId);
        wrapper.eq("tag_id", tagId);

        Integer count = linkTagMapper.selectCount(wrapper);
        if (count <= 1){
            LinkTag linkTag = new LinkTag();
            linkTag.setTagId(tagId);
            linkTag.setLinkId(linkId);
            linkTagMapper.insert(linkTag);
        }
    }

    @Override
    public void removeLinkFromTag(Long linkId, Long tagId) {

        QueryWrapper<LinkTag> qw = new QueryWrapper<LinkTag>();
        qw.eq("link_id", linkId);
        qw.eq("tag_id", tagId);

        linkTagMapper.delete(qw);
    }
}
