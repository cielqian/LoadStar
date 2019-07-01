package com.ciel.loadstar.user.controller;

import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.exceptions.FriendlyException;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.user.entity.User;
import com.ciel.loadstar.user.dto.input.CreateUser;
import com.ciel.loadstar.user.dto.output.UserInfo;
import com.ciel.loadstar.user.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/15
 * @Comment
 */
@Slf4j
@Api("用户账号相关api")
@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @ApiOperation("创建账号")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<User> create(@RequestBody @Valid CreateUser user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new FriendlyException(bindingResult.getFieldError().getDefaultMessage());
        }
        User account = accountService.create(user);
        log.info("create account success, id " + account.getId());
        return ApiReturnUtil.ok("创建成功",account);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ReturnModel delete(@PathVariable("userId") Long userId){
        accountService.delete(userId);
        return ApiReturnUtil.ok("删除成功");
    }

    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public ReturnModel<UserInfo> current(@RequestHeader(Constants.Header_AccountId) String accountId){
        User user = accountService.findByAccountId(accountId);
        //accountService.findByName(principal.getName());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setAccountId(user.getAccountId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setLastSeen(user.getLastSeen());

        return ApiReturnUtil.ok("查询成功",userInfo);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public User query(@PathVariable("id") Long id){
        return accountService.queryById(id);
    }
}
