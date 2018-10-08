package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.link.infrastructure.exceptions.ObjectNotExistingException;
import com.ciel.pocket.link.repository.LinkRepository;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.linkParser.DefaultLinkParser;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    LinkRepository linkRepository;

    @Override
    public String create(Link link) {
        String linkId = UUID.randomUUID().toString().replaceAll("-", "");
        link.setId(linkId);
        link.setCreateTime(new Date());
        linkRepository.save(link);
        return linkId;
    }

    @Override
    public void delete(String linkId) {
        linkRepository.deleteById(linkId);
    }

    @Override
    public void visit(String linkId) {
        Optional<Link> existing = linkRepository.findById(linkId);
        Link  link= existing.get();
        Assert.notNull(link, "链接不存在");

        link.setLastSeen(new Date());
        link.setVisitedCount(link.getVisitedCount()+1);

        linkRepository.save(link);
    }

    @Override
    public PageableListModel<Link> queryList(String accountId) {
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
