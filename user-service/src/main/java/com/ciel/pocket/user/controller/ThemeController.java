package com.ciel.pocket.user.controller;

import com.ciel.pocket.infrastructure.constants.Constants;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.dto.input.UpdateLanguage;
import com.ciel.pocket.user.dto.input.UpdateListType;
import com.ciel.pocket.user.infrastructure.enums.ThemeModuleEnum;
import com.ciel.pocket.user.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
        return ReturnUtils.ok("查询成功",theme);
    }

    @ApiOperation("更新显示方式")
    @RequestMapping(value = "/listType",method = RequestMethod.POST)
    public ReturnModel listTypeEnum(@RequestHeader(Constants.Header_AccountId) Long accountId, @RequestBody UpdateListType updateListType){
        themeService.updateListType(accountId, updateListType.getListType());
        return ReturnUtils.ok("更新成功");
    }

    @ApiOperation("更新语言")
    @RequestMapping(value = "/language",method = RequestMethod.POST)
    public ReturnModel changeLanguage(@RequestHeader(Constants.Header_AccountId) Long accountId, @RequestBody UpdateLanguage language){
        themeService.changeLanguage(accountId, language.getLanguage());
        return ReturnUtils.ok("更新成功");
    }

    @ApiOperation("更新模块显示")
    @RequestMapping(value = "/modules/{moduleName}",method = RequestMethod.POST)
    public ReturnModel triggerShowModules(@RequestHeader(Constants.Header_AccountId) Long accountId, @PathVariable(name = "moduleName") ThemeModuleEnum moduleName){
        themeService.triggerModule(accountId, moduleName);
        return ReturnUtils.ok("更新成功");
    }
}
