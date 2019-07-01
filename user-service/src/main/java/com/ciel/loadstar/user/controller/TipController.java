package com.ciel.loadstar.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.user.entity.Tip;
import com.ciel.loadstar.user.dto.input.ReadTip;
import com.ciel.loadstar.user.service.TipService;
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

        return ApiReturnUtil.ok("查询成功",tips);
    }

    @ApiOperation("更新当前用户Tip")
    @RequestMapping(value = "/read",method = RequestMethod.POST)
    public ReturnModel update(@RequestHeader(Constants.Header_AccountId) Long accountId,@RequestBody ReadTip readTipInput){

        tipService.readTip(accountId, readTipInput.getTip());

        return ApiReturnUtil.ok("更新成功");
    }
}
