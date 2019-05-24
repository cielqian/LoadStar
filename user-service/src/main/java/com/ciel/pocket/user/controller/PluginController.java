package com.ciel.pocket.user.controller;

import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.domain.Plugin;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.dto.input.CreateUser;
import com.ciel.pocket.user.service.PluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
        return ReturnUtils.ok("查询成功",plugins);
    }
}
