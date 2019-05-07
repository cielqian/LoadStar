package com.ciel.pocket.link.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.input.QueryLinkListInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.es.ESRestClient;
import com.ciel.pocket.link.es.model.ESLink;
import com.ciel.pocket.link.es.repository.LinkESRepository;
import com.ciel.pocket.link.mapper.FolderMapper;
import com.ciel.pocket.link.mapper.LinkMapper;
import com.ciel.pocket.link.mapper.LinkTagMapper;
import com.ciel.pocket.link.mapper.VisitRecordMapper;
import com.ciel.pocket.link.model.Folder;
import com.ciel.pocket.link.model.Link;
import com.ciel.pocket.link.model.LinkTag;
import com.ciel.pocket.link.model.VisitRecord;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.linkParser.JsoupLinkParser;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    LinkTagMapper linkTagMapper;

    @Autowired
    FolderMapper folderMapper;

    @Autowired
    VisitRecordMapper visitRecordMapper;

    @Autowired
    JsoupLinkParser linkParser;

    @Autowired
    private KafkaTemplate kafkaTemplate;

//    @Autowired
//    private LinkESRepository linkESRepository;

    @Value("${loadstar.kafka.topic.linkThumbnail}")
    private String linkThumbnailTopic;

    @Autowired
    ESRestClient esRestClient;

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



        RestHighLevelClient client = esRestClient.getClient();

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

        ESLink esLink = new ESLink();
        esLink.setName(link.getName());
        esLink.setTitle(link.getTitle());
        esLink.setTableId(link.getId());
        esLink.setUserId(link.getUserId());

        IndexRequest request = new IndexRequest("loadstar", "links");
        request.source(JSONObject.toJSONString(esLink), XContentType.JSON);

        try {
            IndexResponse response = client.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        return link.getId();
    }

    @Override
    public void delete(Long linkId) {
        Link link = query(linkId);
        Assert.notNull(link, "链接不存在");
        baseMapper.deleteById(linkId);
        //linkRepository.updateSortIndexBatch(link.getUserId(), link.getSortIndex());
    }

    @Override
    public void trash(Long linkId, Long accountId) {
        Folder folder = folderMapper.queryFolderByCode(accountId, "trash");
        if (folder != null){
            baseMapper.updateFolderById(linkId, folder.getId());
        }
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
    public void deleteLinksUnderFolder(Long folderId) {
        baseMapper.deleteByFolder(folderId);
    }

    @Override
    public List<com.ciel.pocket.link.model.Link> queryLinksUnderTag(Long accountId, Long tagId) {
        return baseMapper.queryAllUnderTag(accountId, tagId);
    }

    @Override
    public AnalysisLinkOutput analysis(AnalysisLinkInput input) {
        return linkParser.analysis(input.getUrl());
    }

    @Override
    public void addLinkToTag(Long linkId, Long tagId) {
        LinkTag linkTag = new LinkTag();
        linkTag.setTagId(tagId);
        linkTag.setLinkId(linkId);
        linkTagMapper.insert(linkTag);
    }

    @Override
    public void removeLinkFromTag(Long linkId, Long tagId) {

        QueryWrapper<LinkTag> qw = new QueryWrapper<LinkTag>();
        qw.eq("link_id", linkId);
        qw.eq("tag_id", tagId);

        linkTagMapper.delete(qw);
    }
}
