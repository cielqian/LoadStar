package com.ciel.pocket.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.input.QueryLinkListInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.mapper.FolderMapper;
import com.ciel.pocket.link.mapper.LinkMapper;
import com.ciel.pocket.link.mapper.LinkTagMapper;
import com.ciel.pocket.link.mapper.VisitRecordMapper;
import com.ciel.pocket.link.model.Folder;
import com.ciel.pocket.link.model.Link;
import com.ciel.pocket.link.model.LinkTag;
import com.ciel.pocket.link.model.VisitRecord;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.linkParser.DefaultLinkParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
            qw.and(l -> l.like("name", queryInput.getKeyword()));
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
        return new DefaultLinkParser().analysis(input.getUrl());
    }

    @Override
    public void addLinkToTag(Long linkId, Long tagId) {
        LinkTag linkTag = new LinkTag();
        linkTag.setTagId(tagId);
        linkTag.setLinkId(linkId);
        linkTagMapper.insert(linkTag);
    }
}
