package com.ciel.loadstar.link.controller;

import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.constants.LinkConstants;
import com.ciel.loadstar.infrastructure.dto.web.PageOutput;
import com.ciel.loadstar.infrastructure.dto.web.PageReturnModel;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ApiOperation("创建链接")
    @Caching(evict={
            @CacheEvict(value = "links", key = "'t:-1:u:' + #accountId")
            ,@CacheEvict(value = "links", key = "'f:' + #input.folderId + ':u:' + #accountId")})
    public ReturnModel<Long> createLink(@RequestHeader(Constants.Header_AccountId) Long accountId, @RequestBody @ApiParam(name = "创建链接参数") @Valid CreateLinkInput input){
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

        return ApiReturnUtil.ok("创建成功",linkId);
    }

    @ApiOperation("查询首页链接")
    @RequestMapping(path = "/loadstar", method = RequestMethod.GET)
    @Cacheable(value = "links", key = "'f:1:u:' + #accountId")
    public ReturnModel<List<Link>> queryLoadstarLinks(@RequestHeader(Constants.Header_AccountId) Long accountId){
        List<Link> links = linkService.queryLinksUnderFolder(LinkConstants.LOADSTAR_FOLDER_ID);
        return ApiReturnUtil.ok("查询成功", links);
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

        return ApiReturnUtil.ok("更新成功",linkId);
    }

    @ApiOperation("浏览链接")
    @ApiImplicitParam(name = "linkId", value = "链接Id")
    @RequestMapping(path = "/{linkId}/visit", method = RequestMethod.PUT)
    public ReturnModel visitLink(@PathVariable(name = "linkId") Long linkId){
        linkService.visit(linkId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("分页查询链接")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public PageReturnModel<Link> queryList(@RequestHeader(Constants.Header_AccountId) Long accountId, QueryLinkListInput queryInput){
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

    @ApiOperation("删除链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}", method = RequestMethod.DELETE)
    public ReturnModel deleteLink(@PathVariable(name = "linkId") Long linkId){
        linkService.delete(linkId);
        return ApiReturnUtil.ok("删除成功");
    }

    @ApiOperation("移动链接")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/to/{folderId}", method = RequestMethod.PUT)
    @CacheEvict(value = "links", key = "'t:-1:u:' + #accountId")
    public ReturnModel moveLinkToFolder(@PathVariable(name = "linkId") Long linkId, @PathVariable(name = "folderId") Long folderId){
        linkService.move(linkId, folderId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接添加Tag")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/addTag/{tagId}", method = RequestMethod.PUT)
    public ReturnModel linkAddTag(@PathVariable(name = "linkId") Long linkId, @PathVariable(name = "tagId") Long tagId){
        linkService.addLinkToTag(linkId, tagId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接添加至常用")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/addToOften", method = RequestMethod.PUT)
    public ReturnModel linkAddToOften(@PathVariable(name = "linkId") Long linkId){
        linkService.addLinkToTag(linkId, -1L);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接从常用移除")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/{linkId}/removeFromOften", method = RequestMethod.PUT)
    public ReturnModel linkRemoveFromOften(@PathVariable(name = "linkId") Long linkId){
        linkService.removeLinkFromTag(linkId, -1L);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("链接移到回收站")
    @ApiParam(name = "linkId", value = "链接ID")
    @RequestMapping(path = "/trash/{linkId}", method = RequestMethod.PUT)
    public ReturnModel trashLink(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "linkId") Long linkId){
        linkService.trash(linkId, accountId);
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("上移链接")
    @ApiParam(name = "linkId", value = "链接ID")
        @RequestMapping(path = "/{linkId}/up", method = RequestMethod.PUT)
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
