package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.pocket.link.domain.*;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.repository.*;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.linkParser.DefaultLinkParser;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    LinkRepository linkRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    LinkTagRepository linkTagRepository;

    @Autowired
    VisitRecordRepository visitRecordRepository;

    @Autowired
    LinkRepository1 linkRepository1;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Link link) {
        Integer totalCount = linkRepository.countByUserId(link.getUserId());

        link.setSortIndex(totalCount + 1);
        link.setCreateTime(new Date());

        if (link.getFolderId() == null || link.getFolderId() == 0){
            Folder folder = folderRepository.findFirstByUserIdAndCodeEquals(link.getUserId(), "default");
            if (folder != null){
                link.setFolder(folder);
            }
        }

        linkRepository.save(link);

        link.getTags().forEach(t -> {
            linkTagRepository.test(link.getId(), t.getId());
        });
        return link.getId();
    }

    @Override
    public void delete(Long linkId) {
        Link link = query(linkId);
        Assert.notNull(link, "链接不存在");

        link.setDelete(true);

        linkRepository.updateSortIndexBatch(link.getUserId(), link.getSortIndex());
        linkRepository.save(link);
    }

    @Override
    public void visit(Long linkId) {
        Optional<Link> existing = linkRepository.findById(linkId);
        Link link= existing.get();
        Assert.notNull(link, "链接不存在");

        link.setLastSeen(new Date());
        link.setVisitedCount(link.getVisitedCount()+1);

        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setLink(link);
        visitRecord.setUserId(link.getUserId());
        visitRecordRepository.save(visitRecord);

        linkRepository.save(link);
    }

    @Override
    public void up(Long linkId) {
        Link link = query(linkId);
        if (link.getSortIndex() == 1){
            return;
        }

        Link preLink = linkRepository.findByUserIdEqualsAndSortIndexEquals(link.getUserId(), link.getSortIndex() - 1);
        if (preLink == null){
            return;
        }
        link.setSortIndex(link.getSortIndex() - 1);
        preLink.setSortIndex(preLink.getSortIndex() + 1);

        linkRepository.save(link);
        linkRepository.save(preLink);

    }

    @Override
    public void down(Long linkId) {
        Link link = query(linkId);

        Link nextLink = linkRepository.findByUserIdEqualsAndSortIndexEquals(link.getUserId(), link.getSortIndex() + 1);
        if (nextLink == null){
            return;
        }
        link.setSortIndex(link.getSortIndex() + 1);
        nextLink.setSortIndex(nextLink.getSortIndex() - 1);

        linkRepository.save(link);
        linkRepository.save(nextLink);
    }

    @Override
    public Link query(Long linkId) {
        Optional<Link> existing = linkRepository.findById(linkId);
        Link link= existing.get();
        Assert.notNull(link, "链接不存在");

        return link;
    }

    @Override
    public PageableListModel<Link> queryList(Long accountId) {
        PageableListModel<Link> linkPageableListModel = new PageableListModel<>();
        List links = linkRepository.findAllByUserIdAndIsDeleteEqualsOrderBySortIndex(accountId, false);
        linkPageableListModel.setItems(links);
        linkPageableListModel.setTotal(links.size());

        return linkPageableListModel;
    }

    @Override
    public List<Link> queryTop5List(Long accountId) {
        Sort sort = new Sort(Sort.Direction.DESC, "visitedCount");
        Pageable pageable = PageRequest.of(0, 5, sort);
        return linkRepository.findAllByUserIdAndIsDeleteEquals(pageable, accountId, false).getContent();
    }

    @Override
    public List<Link> queryRecent5List(Long accountId) {
        Sort sort = new Sort(Sort.Direction.DESC, "lastSeen");
        Pageable pageable = PageRequest.of(0, 5, sort);
        return linkRepository.findAllByUserIdAndIsDeleteEquals(pageable, accountId, false).getContent();
    }

    @Override
    public List<Link> queryLinksUnderFolder(Long accountId, Long folderId) {
        return linkRepository.findAllByUserIdAndIsDeleteEqualsAndFolderIdEquals(accountId, false, folderId);
    }

    @Override
    public List<Link> queryLinksUnderTag(Long accountId, Long tagId) {
        QTag t = QTag.tag;
        QLink l = QLink.link;

        Optional<Tag> tagOptional = tagRepository.findById(tagId);
        if (!tagOptional.isPresent()){
            throw new ObjectNotExistingException("Tag不存在");
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return linkRepository1.findAllByUserIdAndIsDeleteEqualsAndTagIdEquals(accountId, tagId);
    }

    @Override
    public AnalysisLinkOutput analysis(AnalysisLinkInput input) {
        return new DefaultLinkParser().analysis(input.getUrl());
    }
}
