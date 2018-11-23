package com.ciel.pocket.link.controller;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.link.domain.Tag;
import com.ciel.pocket.link.domain.UserDetail;
import com.ciel.pocket.link.dto.input.CreateTagInput;
import com.ciel.pocket.link.infrastructure.utils.AuthContext;
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

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ApiOperation("创建标签")
    public ReturnModel<Long> createTag(@RequestBody @ApiParam(name = "创建链接参数") CreateTagInput input, Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        Tag tag = new Tag();
        tag.setName(input.getName());
        tag.setSortIndex(0);
        tag.setUserId(userDetail.getId());
        tag.setSystem(true);
        Long tagId = tagService.create(tag);

        return ReturnUtils.ok("", tagId);
    }

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    @ApiOperation("查询标签")
    public ReturnModel<List<Tag>> queryTag(Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        List<Tag> tags = tagService.queryAllTag(userDetail.getId());

        return ReturnUtils.ok("", tags);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation("删除标签")
    public ReturnModel deleteTag(@PathVariable("id") Long tagId){
        tagService.delete(tagId);
        return ReturnUtils.ok("删除成功");
    }
}
