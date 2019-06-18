package com.ciel.loadstar.link.controller;

import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ReturnUtil;
import com.ciel.loadstar.link.dto.input.CreateFolderInput;
import com.ciel.loadstar.link.dto.output.FolderTreeOutput;
import com.ciel.loadstar.link.entity.Folder;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.service.FolderService;
import com.ciel.loadstar.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 14:06
 */
@Api("文件夹相关api")
@RequestMapping(path = "/api/folder")
@RestController
public class FolderController {

    @Autowired
    FolderService folderService;

    @Autowired
    LinkService linkService;

    @ApiOperation("查询文件夹")
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public ReturnModel<List<FolderTreeOutput>> query(@RequestHeader(Constants.Header_AccountId) Long accountId){
        List<FolderTreeOutput> links = folderService.queryFolderTree(accountId);
        return ReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("查询文件夹")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ReturnModel<List<FolderTreeOutput>> queryById(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "id") Long folderId){
        List<FolderTreeOutput> links = folderService.queryFolderTree(folderId, accountId);
        return ReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("查询文件夹下的书签")
    @RequestMapping(path = "/{id}/link", method = RequestMethod.GET)
    @Cacheable(value = "links", key = "'f:' + #folderId + ':u:' + #accountId", sync = true)
    public ReturnModel<List<Link>> queryLinkUnderFolder(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "id") Long folderId){
        List<Link> links = linkService.queryLinksUnderFolder(accountId, folderId);
        return ReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("清空文件夹下的书签")
    @RequestMapping(path = "/{id}/link", method = RequestMethod.DELETE)
    public ReturnModel deleteLinkUnderFolder(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "id") Long folderId){
        linkService.deleteLinksUnderFolder(accountId, folderId);
        return ReturnUtil.ok("删除成功");
    }

    @ApiOperation("创建文件夹")
    @RequestMapping(path = "/{userId}", method = RequestMethod.POST)
    public ReturnModel createFolderForUser(@RequestHeader(Constants.Header_AccountId) Long accountId,@RequestBody CreateFolderInput createFolderInput){
        Folder folder = new Folder();
        folder.setParentId(createFolderInput.getParentId());
        folder.setName(createFolderInput.getName());
        folder.setUserId(accountId);
        folder.setIsSystem(false);
        folder.setCode(UUID.randomUUID().toString());
        folderService.create(folder);

        return ReturnUtil.ok("创建成功", folder.getId());
    }

    @ApiOperation("创建文件夹")
    @RequestMapping(path = "/current", method = RequestMethod.POST)
    public ReturnModel createFolderForCurrent(@RequestHeader(Constants.Header_AccountId) Long accountId,@RequestBody CreateFolderInput createFolderInput){
        Folder folder = new Folder();
        folder.setParentId(createFolderInput.getParentId());
        folder.setName(createFolderInput.getName());
        folder.setUserId(accountId);
        folder.setIsSystem(false);

        folderService.create(folder);

        return ReturnUtil.ok("创建成功", folder.getId());
    }

    @ApiOperation("删除文件夹")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ReturnModel deleteFolderForCurrent(@PathVariable(name = "id") Long folderId){
        folderService.removeById(folderId);
        return ReturnUtil.ok("删除成功");
    }

    @ApiOperation("创建默认文件夹")
    @RequestMapping(path = "/default/{userId}", method = RequestMethod.POST)
    public ReturnModel createDefault(@PathVariable Long userId){
        Folder defaultFolder = new Folder();
        defaultFolder.setParentId(0L);
        defaultFolder.setName("未归档");
        defaultFolder.setCode("default");
        defaultFolder.setUserId(userId);
        defaultFolder.setIsSystem(true);

        Folder trashFolder = new Folder();
        trashFolder.setParentId(0L);
        trashFolder.setName("回收站");
        trashFolder.setCode("trash");
        trashFolder.setUserId(userId);
        trashFolder.setIsSystem(true);

        Folder loadStarFolder = new Folder();
        loadStarFolder.setParentId(0L);
        loadStarFolder.setName("快捷");
        loadStarFolder.setCode("loadstar");
        loadStarFolder.setUserId(userId);
        loadStarFolder.setIsSystem(true);

        folderService.create(defaultFolder);
        folderService.create(trashFolder);
        folderService.create(loadStarFolder);

        return ReturnUtil.ok("创建成功");
    }
}