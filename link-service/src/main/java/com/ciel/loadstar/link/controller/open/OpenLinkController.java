package com.ciel.loadstar.link.controller.open;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ReturnUtil;
import com.ciel.loadstar.link.dto.input.AnalysisLinkInput;
import com.ciel.loadstar.link.dto.output.AnalysisLinkOutput;
import com.ciel.loadstar.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("公开链接相关api")
@RestController
@RequestMapping(path = "/open/link")
@Slf4j
public class OpenLinkController {
    @Autowired
    LinkService linkService;

    @ApiOperation("解析链接")
    @RequestMapping(path = "/analysis", method = RequestMethod.POST)
    public ReturnModel<AnalysisLinkOutput> analysisLink(@RequestBody AnalysisLinkInput uri){
        log.info("analysis url : " + uri.getUrl());
        AnalysisLinkOutput links = linkService.analysis(uri);
        return ReturnUtil.ok("解析成功", links);
    }
}
