package com.ciel.pocket.link.controller.open;

import com.ciel.pocket.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.link.dto.input.AnalysisLinkInput;
import com.ciel.pocket.link.dto.output.AnalysisLinkOutput;
import com.ciel.pocket.link.dto.output.ReturnModel;
import com.ciel.pocket.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("公开链接相关api")
@RestController
@RequestMapping(path = "/open/link")
@Log
public class OpenLinkController {
    @Autowired
    LinkService linkService;

    @ApiOperation("解析链接")
    @RequestMapping(path = "/analysis", method = RequestMethod.POST)
    public ReturnModel<AnalysisLinkOutput> analysisLink(@RequestBody AnalysisLinkInput uri){
        log.info("analysis url : " + uri.getUrl());
        AnalysisLinkOutput links = linkService.analysis(uri);
        return ReturnModel.OK(links);
    }
}
