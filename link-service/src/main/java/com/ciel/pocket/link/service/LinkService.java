package com.ciel.pocket.link.service;

import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;

import java.util.UUID;

public interface LinkService {
    String create(Link link);

    void delete(String linkId);

    void visit(String linkId);

    PageableListModel<Link> queryList(String accountId);

    AnalysisLinkOutput analysis(AnalysisLinkInput uri);
}
