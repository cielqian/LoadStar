package com.ciel.loadstar.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciel.loadstar.infrastructure.constants.Constants;
import com.ciel.loadstar.infrastructure.dto.web.PageOutput;
import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.infrastructure.utils.SessionResourceUtil;
import com.ciel.loadstar.user.entity.Passbook;
import com.ciel.loadstar.user.service.PassbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/29 11:36
 */
@Slf4j
@Api("用户账号相关api")
@RestController
@RequestMapping(path = "/api/passbook")
public class PassbookController {
    @Autowired
    PassbookService passbookService;

    @ApiOperation("创建账号")
    @RequestMapping(method = RequestMethod.POST)
    public ReturnModel<Passbook> create(@RequestBody @Valid Passbook passbook){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        passbook.setUserId(accountId);
        passbookService.save(passbook);
        log.info("create passbook");
        passbook.setPassword("");
        return ApiReturnUtil.ok("创建成功",passbook);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ReturnModel<PageOutput<Passbook>> getAll(Page page
            , @RequestParam(value = "note", required = false) String note){
        Long accountId = SessionResourceUtil.getCurrentAccountId();
        QueryWrapper<Passbook> qw = new QueryWrapper<Passbook>();
        qw.eq("user_id", accountId);
        if (StringUtils.isNotEmpty(note)){
            qw.like("note", note);
        }
        PageOutput<Passbook> pagePassbook = new PageOutput<>();
        IPage links = passbookService.page(page, qw);
        pagePassbook.setItems(links.getRecords());
        pagePassbook.setTotal(links.getTotal());
        return ApiReturnUtil.ok("查询成功", pagePassbook);
    }

    @ApiOperation("删除账号")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ReturnModel<Long> delete(@PathVariable("id") Long id){
        passbookService.removeById(id);
        log.info("delete passbook id " + id);
        return ApiReturnUtil.ok("删除成功",id);
    }

    @ApiOperation("更新账号")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ReturnModel<Long> update(@PathVariable("id") Long id, @RequestBody @Valid Passbook passbook){
        passbookService.updateById(passbook);
        log.info("update passbook id " + id);
        return ApiReturnUtil.ok("更新成功",id);
    }
}
