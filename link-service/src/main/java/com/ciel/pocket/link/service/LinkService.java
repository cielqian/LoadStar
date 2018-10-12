package com.ciel.pocket.link.service;

import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;

import java.util.UUID;

public interface LinkService {
    Long create(Link link);

    void delete(Long linkId);

    void visit(Long linkId);

    void up(Long linkId);

    void down(Long linkId);

    Link query(Long linkId);

    PageableListModel<Link> queryList(Long accountId);

    AnalysisLinkOutput analysis(AnalysisLinkInput uri);
}
