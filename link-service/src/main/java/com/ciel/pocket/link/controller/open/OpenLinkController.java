package com.ciel.pocket.link.controller.open;

import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.dto.output.ReturnModel;
import com.ciel.pocket.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api("公开链接相关api")
@RestController
@RequestMapping(path = "/open/link")
public class OpenLinkController {
    @Autowired
    LinkService linkService;

    @ApiOperation("解析链接")
    @RequestMapping(path = "/analysis", method = RequestMethod.POST)
    public ReturnModel<AnalysisLinkOutput> analysisLink(@RequestBody AnalysisLinkInput uri){
        AnalysisLinkOutput links = linkService.analysis(uri);
        return ReturnModel.OK(links);
    }
}
