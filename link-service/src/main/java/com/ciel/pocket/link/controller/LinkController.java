package com.ciel.pocket.link.controller;

import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.domain.UserDetail;
import com.ciel.pocket.link.dto.input.CreateLinkInput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.dto.output.ReturnModel;
import com.ciel.pocket.link.infrastructure.utils.AuthContext;
import com.ciel.pocket.link.service.LinkService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Api("链接相关api")
@RestController
@RequestMapping(path = "/api/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ApiOperation("创建链接")
    public ReturnModel<Long> createLink(@RequestBody @ApiParam(name = "创建链接参数") CreateLinkInput input, Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        Link link = new Link();
        link.setUserId(userDetail.getId());
        link.setUrl(input.getUrl());
        link.setTitle(input.getTitle());
        link.setName(input.getName());
        link.setIcon(input.getIcon());
        Long linkId = linkService.create(link);
        return ReturnModel.OK("", linkId);
    }

    @ApiOperation("浏览链接")
    @ApiImplicitParam(name = "linkId", value = "链接Id")
    @RequestMapping(path = "/{linkId}/visit", method = RequestMethod.PUT)
    public ReturnModel visitLink(@PathVariable(name = "linkId") Long linkId){
        linkService.visit(linkId);
        return ReturnModel.OK();
    }

    @ApiOperation("查询链接")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ReturnModel<PageableListModel<Link>> queryList(Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        PageableListModel<Link> links = linkService.queryList(userDetail.getId());
        return ReturnModel.OK(links);
    }

    @ApiOperation("删除链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}", method = RequestMethod.DELETE)
    public ReturnModel deleteLink(@PathVariable(name = "linkId") Long linkId){
        linkService.delete(linkId);
        return ReturnModel.OK();
    }

    @ApiOperation("上移链接")
    @ApiParam(name = "linkId", value = "链接ID")
        @RequestMapping(path = "/{linkId}/up", method = RequestMethod.PUT)
    public ReturnModel upLink(@PathVariable(name = "linkId") Long linkId){
        linkService.up(linkId);
        return ReturnModel.OK();
    }

    @ApiOperation("下移链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/down", method = RequestMethod.PUT)
    public ReturnModel downLink(@PathVariable(name = "linkId") Long linkId){
        linkService.down(linkId);
        return ReturnModel.OK();
    }
}
