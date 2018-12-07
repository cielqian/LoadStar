package com.ciel.pocket.link.controller;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.security.AuthContext;
import com.ciel.pocket.infrastructure.security.UserDetail;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.link.dto.input.CreateTagInput;
import com.ciel.pocket.link.model.Link;
import com.ciel.pocket.link.model.Tag;
import com.ciel.pocket.link.service.LinkService;
import com.ciel.pocket.link.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:20
 */
@Api("标签相关api")
@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @Autowired
    LinkService linkService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ApiOperation("创建标签")
    public ReturnModel<Long> createTag(@RequestBody @ApiParam(name = "创建链接参数") CreateTagInput input, Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        Tag tag = new Tag();
        tag.setName(input.getName());
        tag.setSortIndex(0);
        tag.setUserId(userDetail.getAccountId());
        tag.setIsSystem(true);
        Long tagId = tagService.create(tag);

        return ReturnUtils.ok("", tagId);
    }

    @ApiOperation("查询文件夹下的书签")
    @RequestMapping(path = "/{id}/link", method = RequestMethod.GET)
    public com.ciel.pocket.link.dto.output.ReturnModel<List<Link>> queryLinkUnderTag(@PathVariable(name = "id") Long tagId, Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        List<com.ciel.pocket.link.model.Link> links = linkService.queryLinksUnderTag(userDetail.getAccountId(), tagId);
        return com.ciel.pocket.link.dto.output.ReturnModel.OK(links);
    }

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    @ApiOperation("查询标签")
    public ReturnModel<List<Tag>> queryTag(Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        List<Tag> tags = tagService.queryAllTag(userDetail.getAccountId());

        return ReturnUtils.ok("", tags);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ApiOperation("查询标签")
    public ReturnModel<List<Tag>> queryTag(Principal principal, @RequestParam("keyword") String keyword ){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        List<Tag> tags = tagService.queryAllTag(userDetail.getAccountId(), "%" + keyword + "%");
        return ReturnUtils.ok("", tags);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation("删除标签")
    public ReturnModel deleteTag(@PathVariable("id") Long tagId){
        tagService.delete(tagId);
        return ReturnUtils.ok("删除成功");
    }
}
