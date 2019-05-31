package com.ciel.pocket.user.controller;

import com.ciel.pocket.infrastructure.constants.Constants;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.domain.User;
import com.ciel.pocket.user.dto.input.CreateUser;
import com.ciel.pocket.user.dto.output.UserInfo;
import com.ciel.pocket.user.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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
        return ReturnUtils.ok("创建成功",account);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ReturnModel delete(@PathVariable("userId") Long userId){
        accountService.delete(userId);
        return ReturnUtils.ok("删除成功");
    }

    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public ReturnModel<UserInfo> current(@RequestHeader(Constants.Header_AccountId) String accountId){
        User user = accountService.findByAccountId(accountId);
        //accountService.findByName(principal.getName());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setLastSeen(user.getLastSeen());

        return ReturnUtils.ok("查询成功",userInfo);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public User query(@PathVariable("id") Long id){
        return accountService.queryById(id);
    }
}
