package com.ciel.pocket.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ciel.pocket.infrastructure.constants.Constants;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.domain.Tip;
import com.ciel.pocket.user.service.TipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/16 19:30
 */
@Api("用户Tip api")
@RestController
@RequestMapping(path = "/api/tip")
public class TipController {

    @Autowired
    TipService tipService;

    @ApiOperation("查询当前用户Tip")
    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public ReturnModel<List<Tip>> current(@RequestHeader(Constants.Header_AccountId) Long accountId){

        QueryWrapper<Tip> qw = new QueryWrapper<Tip>();
        qw.eq("user_id", accountId);

        List<Tip> tips = tipService.list(qw);

        return ReturnUtils.ok("查询成功",tips);
    }

    @ApiOperation("更新当前用户Tip")
    @RequestMapping(value = "/read",method = RequestMethod.POST)
    public ReturnModel update(@RequestHeader(Constants.Header_AccountId) Long accountId,@RequestBody String tip){

        tipService.readTip(accountId, tip);

        return ReturnUtils.ok("更新成功");
    }
}
