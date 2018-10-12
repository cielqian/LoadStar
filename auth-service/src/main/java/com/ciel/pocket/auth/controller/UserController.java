package com.ciel.pocket.auth.controller;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.auth.dto.input.CreateUser;
import com.ciel.pocket.auth.service.UserService;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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
//    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<Long> createUser(@Valid @RequestBody CreateUser user){
        User user1 = userService.createUser(new User(user.getUsername(), user.getPassword()));
        return ReturnUtils.ok("创建成功",user1.getId());
    }

    @ApiOperation(value="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户账号", required = true, dataType = "String")
    })
    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
    public ReturnModel deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return ReturnUtils.ok("删除成功");
    }
}
