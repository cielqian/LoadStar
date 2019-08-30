package com.ciel.loadstar.infrastructure.dto.web;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.http.HttpStatus;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
@Data
@ApiModel
public class PageReturnModel<T> extends ReturnModel<PageOutput<T>> {
    public PageReturnModel(int status, String message, PageOutput<T> data) {
        super(status, message, data);
    }
}
