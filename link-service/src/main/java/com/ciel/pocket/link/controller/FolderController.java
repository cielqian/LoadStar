package com.ciel.pocket.link.controller;

import com.ciel.pocket.link.domain.Folder;
import com.ciel.pocket.link.domain.Link;
import com.ciel.pocket.link.domain.UserDetail;
import com.ciel.pocket.link.dto.input.CreateFolderInput;
import com.ciel.pocket.link.dto.output.FolderTreeOutput;
import com.ciel.pocket.link.dto.output.PageableListModel;
import com.ciel.pocket.link.dto.output.ReturnModel;
import com.ciel.pocket.link.infrastructure.utils.AuthContext;
import com.ciel.pocket.link.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @ApiOperation("查询文件夹")
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public ReturnModel<List<FolderTreeOutput>> query(Principal principal){
        UserDetail userDetail = AuthContext.getUserDetail(principal);
        List<FolderTreeOutput> links = folderService.queryFolderTree(userDetail.getId());
        return ReturnModel.OK(links);
    }

    @ApiOperation("创建文件夹")
    @RequestMapping(path = "/{userId}", method = RequestMethod.POST)
    public ReturnModel createFolderForUser(Principal principal,@RequestBody CreateFolderInput createFolderInput){
        UserDetail userDetail = AuthContext.getUserDetail(principal);

        Folder folder = new Folder();
        folder.setParentId(createFolderInput.getParentId());
        folder.setName(createFolderInput.getName());
        folder.setUserId(userDetail.getId());
        folder.setSystem(false);

        folderService.create(folder);

        return ReturnModel.OK();
    }

    @ApiOperation("创建文件夹")
    @RequestMapping(path = "/current", method = RequestMethod.POST)
    public ReturnModel createFolderForCurrent(@PathVariable Long userId,@RequestBody CreateFolderInput createFolderInput){
        Folder folder = new Folder();
        folder.setParentId(createFolderInput.getParentId());
        folder.setName(createFolderInput.getName());
        folder.setUserId(userId);
        folder.setSystem(false);

        folderService.create(folder);

        return ReturnModel.OK();
    }

    @ApiOperation("创建默认文件夹")
    @RequestMapping(path = "/default/{userId}", method = RequestMethod.POST)
    public ReturnModel createDefault(@PathVariable Long userId){
        Folder defaultFolder = new Folder();
        defaultFolder.setParentId(0L);
        defaultFolder.setName("未归档");
        defaultFolder.setCode("default");
        defaultFolder.setUserId(userId);
        defaultFolder.setSystem(true);

        Folder trashFolder = new Folder();
        trashFolder.setParentId(0L);
        trashFolder.setName("回收站");
        trashFolder.setCode("trash");
        trashFolder.setUserId(userId);
        trashFolder.setSystem(true);

        folderService.create(defaultFolder);
        folderService.create(trashFolder);

        return ReturnModel.OK();
    }

}
