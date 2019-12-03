package com.ciel.loadstar.user.controller;

import com.ciel.loadstar.infrastructure.dto.web.ReturnModel;
import com.ciel.loadstar.infrastructure.utils.ApiReturnUtil;
import com.ciel.loadstar.user.dto.input.WebMetric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/metric")
public class MetricController {
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ReturnModel collect(WebMetric metric){
        return ApiReturnUtil.ok("提交成功");
    }
}
