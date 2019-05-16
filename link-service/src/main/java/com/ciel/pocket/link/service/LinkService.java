package com.ciel.pocket.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.input.QueryLinkListInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.model.Link;

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

    void deleteLinksUnderFolder(Long folderId);

    List<com.ciel.pocket.link.model.Link> queryLinksUnderTag(Long accountId, Long tagId);

    AnalysisLinkOutput analysis(AnalysisLinkInput uri);

    void addLinkToTag(Long linkId, Long tagId);

    void removeLinkFromTag(Long linkId, Long tagId);

}
