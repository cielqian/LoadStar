package com.ciel.pocket.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciel.pocket.infrastructure.dto.web.PageOutput;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import com.ciel.pocket.infrastructure.utils.ReturnUtils;
import com.ciel.pocket.user.domain.Passbook;
import com.ciel.pocket.user.service.PassbookService;
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
    public ReturnModel<Passbook> create(@RequestBody @Valid Passbook passbook, BindingResult bindingResult){
        passbookService.save(passbook);
        log.info("create passbook");
        passbook.setPassword("");
        return ReturnUtils.ok("创建成功",passbook);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ReturnModel<PageOutput<Passbook>> getAll(Page page){
        PageOutput<Passbook> pagePassbook = new PageOutput<>();
        IPage links = passbookService.page(page);
        pagePassbook.setItems(links.getRecords());
        pagePassbook.setTotal(links.getTotal());
        return ReturnUtils.ok("查询成功", pagePassbook);
    }
}
