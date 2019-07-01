package com.ciel.loadstar.user.controller;

import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.user.entity.Theme;
import com.ciel.loadstar.user.dto.input.UpdateLanguage;
import com.ciel.loadstar.user.dto.input.UpdateListType;
import com.ciel.loadstar.user.enums.ThemeModuleEnum;
import com.ciel.loadstar.user.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("用户主题相关api")
@RestController
@RequestMapping(path = "/api/theme")
public class ThemeController {
    @Autowired
    ThemeService themeService;

    @ApiOperation("查询当前用户主题")
    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public ReturnModel<Theme> current(@RequestHeader(Constants.Header_AccountId) Long accountId){
        Theme theme = themeService.queryByAccountId(accountId);
        return ApiReturnUtil.ok("查询成功",theme);
    }

    @ApiOperation("更新显示方式")
    @RequestMapping(value = "/listType",method = RequestMethod.POST)
    public ReturnModel listTypeEnum(@RequestHeader(Constants.Header_AccountId) Long accountId, @RequestBody UpdateListType updateListType){
        themeService.updateListType(accountId, updateListType.getListType());
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("更新语言")
    @RequestMapping(value = "/language",method = RequestMethod.POST)
    public ReturnModel changeLanguage(@RequestHeader(Constants.Header_AccountId) Long accountId, @RequestBody UpdateLanguage language){
        themeService.changeLanguage(accountId, language.getLanguage());
        return ApiReturnUtil.ok("更新成功");
    }

    @ApiOperation("更新模块显示")
    @RequestMapping(value = "/modules/{moduleName}",method = RequestMethod.POST)
    public ReturnModel triggerShowModules(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "moduleName") ThemeModuleEnum moduleName){
        themeService.triggerModule(accountId, moduleName);
        return ApiReturnUtil.ok("更新成功");
    }
}
