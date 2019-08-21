package com.ciel.loadstar.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.infrastructure.dto.web.PageOutput;
import com.ciel.loadstar.link.dto.input.AnalysisLinkInput;
import com.ciel.loadstar.link.dto.input.QueryLinkListInput;
import com.ciel.loadstar.link.dto.output.AnalysisLinkOutput;
import com.ciel.loadstar.link.dto.output.QueryVisitRecordOutput;
import com.ciel.loadstar.link.entity.DailyStatistical;
import com.ciel.loadstar.link.entity.Link;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;

public interface LinkService extends IService<Link> {

    @CacheEvict(value = "links", allEntries = true)
    Long create(Link link, List<Long> tags);

    Long update(Link link, List<Long> tags);

    @CacheEvict(value = "links", allEntries = true)
    void delete(Long accountId, Long linkId);

    @CacheEvict(value = "links", allEntries = true)
    void trash(Long accountId, Long linkId);

    @CacheEvict(value = "links", allEntries = true)
    void move(Long linkId, Long folderId);

    void visit(Long linkId);

    void up(Long linkId);

    void down(Long linkId);

    Link query(Long linkId);

    PageOutput<Link> queryPageList(Long accountId, QueryLinkListInput queryInput);

    PageOutput<Link> fullTextSearch(Long accountId, QueryLinkListInput queryInput);

    List<Link> queryTop5List(Long accountId);

    List<Link> queryRecent5List(Long accountId);

    @Cacheable(value = "links", key = "'f:' + #folderId + ':u:' + #accountId")
    List<Link> queryLinksUnderFolder(Long accountId, Long folderId);

    void deleteLinksUnderFolder(Long accountId, Long folderId);

    @Cacheable(value = "links", key = "'t:' + #tagId + ':u:' + #accountId")
    List<Link> queryLinksWithTag(Long accountId, Long tagId);

    AnalysisLinkOutput analysis(AnalysisLinkInput uri);

    void addLinkToTag(Long linkId, Long tagId);

    void removeLinkFromTag(Long linkId, Long tagId);

    List<DailyStatistical> queryDailyStatistical(Long accountId, Date day, String type);

    List<QueryVisitRecordOutput> queryVisitRecords(Long accountId, Date day);

}
