package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
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
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkTagMapper linkTagMapper;

    @Autowired
    LinkMapper linkMapper;

    @Autowired
    FolderMapper folderMapper;

    @Autowired
    VisitRecordMapper visitRecordMapper;

    @Override
    public Long create(Link link, List<Long> tags) {
        Integer totalCount = linkMapper.countByUser(link.getUserId());

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

        linkMapper.insert(link);

        tags.forEach(tagId -> {
            LinkTag linkTag = new LinkTag();
            linkTag.setLinkId(link.getId());
            linkTag.setTagId(tagId);
            linkTagMapper.insert(linkTag);
        });
        return link.getId();
    }

    @Override
    public void delete(Long linkId) {
        Link link = query(linkId);
        Assert.notNull(link, "链接不存在");
        linkMapper.deleteById(linkId);
        //linkRepository.updateSortIndexBatch(link.getUserId(), link.getSortIndex());
    }

    @Override
    public void trash(Long linkId, Long accountId) {
        Folder folder = folderMapper.queryFolderByCode(accountId, "trash");
        if (folder != null){
            linkMapper.updateFolderById(linkId, accountId);
        }
    }

    @Override
    public void visit(Long linkId) {
        Link link = linkMapper.selectByPrimaryKey(linkId);
        Assert.notNull(link, "链接不存在");

        link.setLastSeen(new Date());
        link.setVisitedCount(link.getVisitedCount()+1);

        linkMapper.insert(link);

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
        Link link = linkMapper.selectByPrimaryKey(linkId);
        Assert.notNull(link, "链接不存在");

        return link;
    }

    @Override
    public PageableListModel<Link> queryList(Long accountId) {
        PageableListModel<Link> linkPageableListModel = new PageableListModel<>();
        List links = linkMapper.queryAll(accountId);
        linkPageableListModel.setItems(links);
        linkPageableListModel.setTotal(links.size());

        return linkPageableListModel;
    }

    @Override
    public List<Link> queryTop5List(Long accountId) {
        PageHelper.startPage(1, 5);

        Sort sort = new Sort(Sort.Direction.DESC, "visitedCount");
        Pageable pageable = PageRequest.of(0, 5, sort);
        return linkMapper.queryAll(accountId);
    }

    @Override
    public List<Link> queryRecent5List(Long accountId) {
        PageHelper.startPage(1, 5);

        Sort sort = new Sort(Sort.Direction.DESC, "lastSeen");
        return linkMapper.queryAll(accountId);
    }

    @Override
    public List<Link> queryLinksUnderFolder(Long accountId, Long folderId) {
        return linkMapper.queryAllUnderFolder(accountId, folderId);
    }

    @Override
    public List<com.ciel.pocket.link.model.Link> queryLinksUnderTag(Long accountId, Long tagId) {
        return linkMapper.queryAllUnderTag(accountId, tagId);
    }

    @Override
    public AnalysisLinkOutput analysis(AnalysisLinkInput input) {
        return new DefaultLinkParser().analysis(input.getUrl());
    }
}
