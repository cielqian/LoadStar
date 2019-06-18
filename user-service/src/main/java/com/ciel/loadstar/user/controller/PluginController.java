package com.ciel.loadstar.user.controller;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ReturnUtil;
import com.ciel.loadstar.user.entity.Plugin;
import com.ciel.loadstar.user.service.PluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/22 13:39
 */
@Api("插件相关api")
@RestController
@RequestMapping(path = "/api/plugin")
public class PluginController {

    @Autowired
    PluginService pluginService;

    @ApiOperation("查询插件")
    @RequestMapping(method = RequestMethod.GET)
    public ReturnModel<List<Plugin>> query(){
        List plugins = pluginService.list();
        return ReturnUtil.ok("查询成功",plugins);
    }
}
