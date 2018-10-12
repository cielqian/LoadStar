package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.repository.LinkRepository;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.linkParser.DefaultLinkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    LinkRepository linkRepository;

    @Override
    public Long create(Link link) {
        link.setCreateTime(new Date());
        linkRepository.save(link);
        return link.getId();
    }

    @Override
    public void delete(Long linkId) {
        linkRepository.deleteById(linkId);
    }

    @Override
    public void visit(Long linkId) {
        Optional<Link> existing = linkRepository.findById(linkId);
        Link  link= existing.get();
        Assert.notNull(link, "链接不存在");

        link.setLastSeen(new Date());
        link.setVisitedCount(link.getVisitedCount()+1);

        linkRepository.save(link);
    }

    @Override
    public PageableListModel<Link> queryList(Long accountId) {
        PageableListModel<Link> linkPageableListModel = new PageableListModel<>();
        List links = linkRepository.findAllByUserId(accountId);
        linkPageableListModel.setItems(links);
        linkPageableListModel.setTotal(links.size());

        return linkPageableListModel;
    }

    @Override
    public AnalysisLinkOutput analysis(AnalysisLinkInput input) {
        return new DefaultLinkParser().analysis(input.getUrl());
    }
}
