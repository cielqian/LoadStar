package com.ciel.loadstar.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.link.dto.input.AnalysisLinkInput;
import com.ciel.loadstar.link.dto.input.QueryLinkListInput;
import com.ciel.loadstar.link.dto.output.AnalysisLinkOutput;
import com.ciel.loadstar.link.dto.output.PageableListModel;
import com.ciel.loadstar.link.dto.output.QueryVisitRecordOutput;
import com.ciel.loadstar.link.entity.DailyStatistical;
import com.ciel.loadstar.link.entity.Link;

import java.util.Date;
import java.util.List;

public interface LinkService extends IService<Link> {
    Long create(Link link, List<Long> tags);

    Long update(Link link, List<Long> tags);

    void delete(Long linkId);

    void trash(Long linkId, Long accountId);

    void move(Long linkId, Long folderId);

    void visit(Long linkId);

    void up(Long linkId);

    void down(Long linkId);

    Link query(Long linkId);

    PageableListModel<Link> queryPageList(Long accountId, QueryLinkListInput queryInput);

    PageableListModel<Link> fullTextSearch(Long accountId, QueryLinkListInput queryInput);

    List<Link> queryTop5List(Long accountId);

    List<Link> queryRecent5List(Long accountId);

    List<Link> queryLinksUnderFolder(Long accountId, Long folderId);

    void deleteLinksUnderFolder(Long accountId,Long folderId);

    List<Link> queryLinksUnderTag(Long accountId, Long tagId);

    AnalysisLinkOutput analysis(AnalysisLinkInput uri);

    void addLinkToTag(Long linkId, Long tagId);

    void removeLinkFromTag(Long linkId, Long tagId);

    List<DailyStatistical> queryDailyStatistical(Long accountId, Date day, String type);

    List<QueryVisitRecordOutput> queryVisitRecords(Long accountId, Date day);

}
