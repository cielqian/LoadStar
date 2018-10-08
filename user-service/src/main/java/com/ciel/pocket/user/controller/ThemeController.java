package com.ciel.pocket.user.controller;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.dto.input.UpdateListType;
import com.ciel.pocket.user.infrastructure.utils.AuthContext;
import com.ciel.pocket.user.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api("用户主题相关api")
@RestController
@RequestMapping(path = "/api/theme")
public class ThemeController {
    @Autowired
    ThemeService themeService;

    @ApiOperation("查询当前用户主题")
    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public ReturnModel<Theme> current(Principal principal){
        Theme theme = themeService.queryByUserId(AuthContext.getUserDetail(principal).getId());
        return ReturnUtils.ok("查询成功",theme);
    }

    @ApiOperation("更新显示方式")
    @RequestMapping(value = "/listType",method = RequestMethod.POST)
    public ReturnModel listTypeEnum(Principal principal, @RequestBody UpdateListType updateListType){
        themeService.updateListType(AuthContext.getUserDetail(principal).getId(),
                updateListType.getListType());
        return ReturnUtils.ok("更新成功");
    }
}
