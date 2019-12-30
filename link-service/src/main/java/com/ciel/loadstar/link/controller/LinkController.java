package com.ciel.loadstar.link.controller;

import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.constants.LinkConstants;
import com.ciel.loadstar.infrastructure.dto.web.PageOutput;
import com.ciel.loadstar.infrastructure.dto.web.PageReturnModel;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.dto.input.CreateLinkInput;
import com.ciel.loadstar.link.dto.input.QueryLinkListInput;
import com.ciel.loadstar.link.dto.input.UpdateLinkInput;
import com.ciel.loadstar.link.dto.output.QueryCalendarOutput;
import com.ciel.loadstar.link.dto.output.QueryVisitRecordOutput;
import com.ciel.loadstar.link.entity.DailyStatistical;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.service.LinkService;
import com.ciel.loadstar.link.service.TagService;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 链接接口
 * @author ciel
 */
@Api("链接相关api")
@RestController
@RequestMapping(path = "/api/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @Autowired
    TagService tagService;

    @PostMapping
    @ApiOperation("创建链接")
    public ReturnModel<Long> createLink(@RequestBody @ApiParam(name = "创建链接参数") @Valid CreateLinkInput input){
        Long accountId = SessionResourceUtil.getCurrentAccountId();

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
            linkService.addLinkToTag(accountId, linkId, LinkConstants.TAG_DASH_ID);
        }

        return ApiReturnUtil.ok("创建成功",linkId);
    }

    @PutMapping
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

        return ApiReturnUtil.ok("更新成功",linkId);
    }

    @ApiOperation("删除链接")
    @DeleteMapping(path = "/{linkId}")
    public ReturnModel deleteLink(@PathVariable(name = "linkId") @ApiParam("链接ID") Long linkId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        linkService.delete(accountId, linkId);
        return ApiReturnUtil.ok("删除成功");
    }

    @ApiOperation("查询首页链接")
    @GetMapping("/loadstar")
    public ReturnModel<List<Link>> queryLoadstarLinks(){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        List<Link> links = linkService.queryLinksWithTag(accountId, LinkConstants.TAG_DASH_ID);
        return ApiReturnUtil.ok("查询成功", links);
    }

    @ApiOperation("浏览链接")
    @ApiImplicitParam(name = "linkId", value = "链接Id")
    @RequestMapping(path = "/{linkId}/visit", method = RequestMethod.PUT)
    public ReturnModel visitLink(@PathVariable(name = "linkId") Long linkId){
        linkService.visit(linkId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("分页查询链接")
    @GetMapping("/page")
    public PageReturnModel<Link> queryList(QueryLinkListInput queryInput){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        PageOutput<Link> pageLinks = linkService.queryPageList(accountId, queryInput);
        return ApiReturnUtil.page(pageLinks);
    }

    @ApiOperation("全文搜索")
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public PageReturnModel<Link> search(@RequestHeader(Constants.Header_AccountId) Long accountId, QueryLinkListInput queryInput){
        PageOutput<Link> links = linkService.fullTextSearch(accountId, queryInput);
        return ApiReturnUtil.page(links);
    }

    @ApiOperation("查询最近访问链接")
    @RequestMapping(path = "/recent", method = RequestMethod.GET)
    public ReturnModel<List<Link>> queryRecentList(@RequestHeader(Constants.Header_AccountId) Long accountId){
        List<Link> links = linkService.queryRecent5List(accountId);
        return ApiReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("查询最常访问链接")
    @RequestMapping(path = "/top", method = RequestMethod.GET)
    public ReturnModel<List<Link>> queryTopList(@RequestHeader(Constants.Header_AccountId) Long accountId){
        List<Link> links = linkService.queryTop5List(accountId);
        return ApiReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("移动链接")
    @PutMapping("/{linkId}/toFolder/{folderId}")
    public ReturnModel moveLinkToFolder(@PathVariable(name = "linkId") @NotNull @ApiParam("链接Id") Long linkId
            , @PathVariable(name = "folderId") @NotNull @ApiParam("文件夹Id") Long folderId){
        linkService.move(linkId, folderId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接添加Tag")
    @PutMapping(path = "/{linkId}/addTag/{tagId}")
    public ReturnModel linkAddTag(@PathVariable(name = "linkId") @ApiParam("链接Id") Long linkId
            , @PathVariable(name = "tagId") @ApiParam("标签Id") Long tagId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        linkService.addLinkToTag(accountId, linkId, tagId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接添加至首页常用")
    @PutMapping(path = "/{linkId}/addToDash")
    public ReturnModel linkAddToLoadstar(@PathVariable(name = "linkId") @ApiParam("链接Id") Long linkId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        linkService.addLinkToTag(accountId, linkId, LinkConstants.TAG_DASH_ID);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接从常用移除")
    @ApiParam(name = "linkId", value = "链接ID")
    @PutMapping(path = "/{linkId}/removeFromDash")
    public ReturnModel linkRemoveFromLoadstar(@PathVariable(name = "linkId") @ApiParam("链接Id") Long linkId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        linkService.removeLinkFromTag(accountId, linkId, LinkConstants.TAG_DASH_ID);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接移到回收站")
    @PutMapping(path = "/trash/{linkId}")
    public ReturnModel trash(@PathVariable(name = "linkId") @ApiParam("链接ID") Long linkId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        linkService.trash(accountId, linkId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("上移链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @PutMapping(path = "/{linkId}/up")
    public ReturnModel upLink(@PathVariable(name = "linkId") Long linkId){
        linkService.up(linkId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("下移链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/down", method = RequestMethod.PUT)
    public ReturnModel downLink(@PathVariable(name = "linkId") Long linkId){
        linkService.down(linkId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("生成短链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/down", method = RequestMethod.GET)
    public ReturnModel shortLink(@PathVariable(name = "linkId") Long linkId){
        linkService.down(linkId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("当月浏览记录")
    @RequestMapping(path = "/calendar/visit/month/{month}", method = RequestMethod.GET)
    public ReturnModel visitMonth(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "month") String month){
        Date date = null;
        try {
            date = DateUtils.parseDate(month, "YYYY-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<DailyStatistical> calendars = linkService.queryDailyStatistical(accountId, date, "VISIT");
        List<QueryCalendarOutput> out = new ArrayList<>();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        for (DailyStatistical calendar: calendars){
            out.add(mapper.map(calendar,  QueryCalendarOutput.class));
        }

        return ApiReturnUtil.ok("更新成功", out);
    }

    @ApiOperation("当日浏览记录")
    @RequestMapping(path = "/calendar/visit/day/{day}", method = RequestMethod.GET)
    public ReturnModel visitDay(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "day") String day){
        Date date = null;
        try {
            date = DateUtils.parseDate(day, "YYYY-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<QueryVisitRecordOutput> out = linkService.queryVisitRecords(accountId, date);

        return ApiReturnUtil.ok("更新成功", out);
    }
}
