package com.ciel.loadstar.link.controller;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.link.dto.input.CreateFolderInput;
import com.ciel.loadstar.link.dto.output.FolderTreeOutput;
import com.ciel.loadstar.link.entity.Folder;
import com.ciel.loadstar.link.entity.Link;
import com.ciel.loadstar.link.service.FolderService;
import com.ciel.loadstar.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @ApiOperation("查询当前用户所有文件夹")
    @GetMapping(path = "/current")
    public ReturnModel<List<FolderTreeOutput>> query(){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        List<FolderTreeOutput> links = folderService.queryFolderTree(accountId);
        return ApiReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("根据id查询文件夹")
    @GetMapping(path = "/{id}")
    public ReturnModel<List<FolderTreeOutput>> queryById(@PathVariable(name = "id") @NotNull Long folderId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        List<FolderTreeOutput> links = folderService.queryFolderTree(folderId, accountId);
        return ApiReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("查询文件夹下的书签")
    @GetMapping(path = "/{id}/link")
    public ReturnModel<List<Link>> queryLinkUnderFolder(@PathVariable(name = "id") @NotNull Long folderId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        List<Link> links = linkService.queryLinksUnderFolder(accountId, folderId);
        return ApiReturnUtil.ok("查询成功",links);
    }

    @ApiOperation("清空文件夹下的书签")
    @DeleteMapping(path = "/{id}/link")
    public ReturnModel deleteLinkUnderFolder(@PathVariable(name = "id") @NotNull Long folderId){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        linkService.deleteLinksUnderFolder(accountId, folderId);
        return ApiReturnUtil.ok("删除成功");
    }

    @ApiOperation("创建文件夹")
    @PostMapping(path = "/{userId}")
    public ReturnModel createFolderForUser(@RequestBody CreateFolderInput createFolderInput){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        Folder folder = new Folder();
        folder.setParentId(createFolderInput.getParentId());
        folder.setName(createFolderInput.getName());
        folder.setUserId(accountId);
        folder.setIsSystem(false);
        folder.setCode(UUID.randomUUID().toString());
        folderService.create(folder);

        return ApiReturnUtil.ok("创建成功", folder.getId());
    }

    @ApiOperation("创建文件夹")
    @PostMapping(path = "/current")
    public ReturnModel createFolderForCurrent(@RequestBody CreateFolderInput createFolderInput){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        Folder folder = new Folder();
        folder.setParentId(createFolderInput.getParentId());
        folder.setName(createFolderInput.getName());
        folder.setUserId(accountId);
        folder.setIsSystem(false);

        folderService.create(folder);

        return ApiReturnUtil.ok("创建成功", folder.getId());
    }

    @ApiOperation("删除文件夹")
    @DeleteMapping(path = "/{id}")
    public ReturnModel deleteFolderForCurrent(@PathVariable(name = "id") @NotNull Long folderId){
        folderService.removeById(folderId);
        return ApiReturnUtil.ok("删除成功");
    }

}
