package com.ciel.loadstar.auth.controller;

import com.ciel.loadstar.auth.dto.input.CreateUser;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ReturnUtil;
import com.ciel.loadstar.auth.entity.User;
import com.ciel.loadstar.auth.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenStore tokenStore;

    @PreAuthorize(value = "#oauth2.hasScope('ui,server')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal, OAuth2Authentication oAuth2Authentication) {

        OAuth2AuthenticationDetails details
                = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
        OAuth2AccessToken accessToken = tokenStore
                .readAccessToken(details.getTokenValue());
        return principal;
    }

    @ApiOperation(value="创建用户")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<Long> createUser(@Valid @RequestBody CreateUser user){
        User user1 = userService.createUser(new User(user.getUsername(), user.getPassword()));
        return ReturnUtil.ok("创建成功",user1.getId());
    }

//    @ApiOperation(value="删除用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "用户账号", required = true, dataType = "String")
//    })
//    @PreAuthorize("#oauth2.hasScope('server')")
//    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
//    public ReturnModel deleteUser(@PathVariable("username") String username){
//        userService.deleteUser(username);
//        return ReturnUtil.ok("删除成功");
//    }

    @ApiOperation(value="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String")
    })
    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/{userId}",method = RequestMethod.DELETE)
    public ReturnModel deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return ReturnUtil.ok("删除成功");
    }
}
