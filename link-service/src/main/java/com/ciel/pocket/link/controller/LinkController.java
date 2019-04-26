package com.ciel.pocket.link.controller;

import com.ciel.pocket.infrastructure.constants.Constants;
import com.ciel.pocket.infrastructure.security.UserDetail;
import com.ciel.pocket.link.dto.input.CreateLinkInput;
import com.ciel.pocket.link.dto.input.QueryLinkListInput;
import com.ciel.pocket.link.dto.input.UpdateLinkInput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.dto.output.ReturnModel;
import com.ciel.pocket.link.model.Link;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.TagService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Api("链接相关api")
@RestController
@RequestMapping(path = "/api/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @Autowired
    TagService tagService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ApiOperation("创建链接")
    public ReturnModel<Long> createLink(@RequestHeader(Constants.Header_AccountId) Long accountId, @RequestBody @ApiParam(name = "创建链接参数") CreateLinkInput input, Principal principal){
        Link link = new Link();
        link.setUserId(accountId);
        link.setUrl(input.getUrl());

        if (StringUtils.isBlank(input.getTitle())){
            link.setTitle(input.getName());
        }else{
            link.setTitle(input.getTitle());
        }

        link.setName(input.getName());
        link.setIcon(input.getIcon());
        link.setFolderId(input.getFolderId());

        Long linkId = linkService.create(link, input.getTags());

        if (input.isOften()){
            linkService.addLinkToTag(linkId, -1L);
        }

        return ReturnModel.OK("", linkId);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    @ApiOperation("更新链接")
    public ReturnModel<Long> updateLink(@RequestBody @ApiParam(name = "更新链接参数") UpdateLinkInput input){

        Link link = linkService.query(input.getId());
        Assert.notNull(link, "链接不存在");

        link.setTitle(input.getTitle());
        link.setName(input.getName());
        link.setFolderId(input.getFolderId());
        link.setName(input.getName());
        link.setIcon(input.getIcon());

        Long linkId = linkService.update(link, input.getTags());

//        if (input.isOften()){
//            linkService.addLinkToTag(linkId, -1L);
//        }

        return ReturnModel.OK("", linkId);
    }

    @ApiOperation("浏览链接")
    @ApiImplicitParam(name = "linkId", value = "链接Id")
    @RequestMapping(path = "/{linkId}/visit", method = RequestMethod.PUT)
    public ReturnModel visitLink(@PathVariable(name = "linkId") Long linkId){
        linkService.visit(linkId);
        return ReturnModel.OK();
    }

    @ApiOperation("分页查询链接")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ReturnModel<PageableListModel<Link>> queryList(@RequestHeader(Constants.Header_AccountId) Long accountId, QueryLinkListInput queryInput){
        PageableListModel<Link> links = linkService.queryPageList(accountId, queryInput);
        return ReturnModel.OK(links);
    }

    @ApiOperation("查询最近访问链接")
    @RequestMapping(path = "/recent", method = RequestMethod.GET)
    public ReturnModel<List<Link>> queryRecentList(@RequestHeader(Constants.Header_AccountId) Long accountId){
        List<Link> links = linkService.queryRecent5List(accountId);
        return ReturnModel.OK(links);
    }

    @ApiOperation("查询最常访问链接")
    @RequestMapping(path = "/top", method = RequestMethod.GET)
    public ReturnModel<List<Link>> queryTopList(@RequestHeader(Constants.Header_AccountId) Long accountId){
        List<Link> links = linkService.queryTop5List(accountId);
        return ReturnModel.OK(links);
    }

    @ApiOperation("删除链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}", method = RequestMethod.DELETE)
    public ReturnModel deleteLink(@PathVariable(name = "linkId") Long linkId){
        linkService.delete(linkId);
        return ReturnModel.OK();
    }

    @ApiOperation("移动链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/to/{folderId}", method = RequestMethod.PUT)
    public ReturnModel moveLinkToFolder(@PathVariable(name = "linkId") Long linkId, @PathVariable(name = "folderId") Long folderId){
        linkService.move(linkId, folderId);
        return ReturnModel.OK();
    }

    @ApiOperation("链接添加Tag")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/addTag/{tagId}", method = RequestMethod.PUT)
    public ReturnModel linkAddTag(@PathVariable(name = "linkId") Long linkId, @PathVariable(name = "tagId") Long tagId){
        linkService.addLinkToTag(linkId, tagId);
        return ReturnModel.OK();
    }

    @ApiOperation("链接添加至常用")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/addToOften", method = RequestMethod.PUT)
    public ReturnModel linkAddToOften(@PathVariable(name = "linkId") Long linkId){
        linkService.addLinkToTag(linkId, -1L);
        return ReturnModel.OK();
    }

    @ApiOperation("链接从常用移除")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/removeFromOften", method = RequestMethod.PUT)
    public ReturnModel linkRemoveFromOften(@PathVariable(name = "linkId") Long linkId){
        linkService.removeLinkFromTag(linkId, -1L);
        return ReturnModel.OK();
    }

    @ApiOperation("链接移到回收站")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/trash/{linkId}", method = RequestMethod.PUT)
    public ReturnModel trashLink(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "linkId") Long linkId){
        linkService.trash(linkId, accountId);
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

    @ApiOperation("生成短链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/down", method = RequestMethod.GET)
    public ReturnModel shortLink(@PathVariable(name = "linkId") Long linkId){
        linkService.down(linkId);
        return ReturnModel.OK();
    }
}
