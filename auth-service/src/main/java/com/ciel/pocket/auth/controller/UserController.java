package com.ciel.pocket.auth.controller;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.auth.dto.output.ReturnModel;
import com.ciel.pocket.auth.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    @ApiOperation(value="创建用户")
    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<String> createUser(@Valid @RequestBody User user){
        userService.createUser(user);
        return ReturnModel.OK("创建成功",user.getId());
    }

    @ApiOperation(value="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户账号", required = true, dataType = "String")
    })
    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
    public ReturnModel deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return ReturnModel.OK();
    }
}
