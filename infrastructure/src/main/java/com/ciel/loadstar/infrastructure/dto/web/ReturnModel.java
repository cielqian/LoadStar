package com.ciel.loadstar.infrastructure.dto.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
@Data
@ApiModel
public class ReturnModel<T> implements Serializable {

    @ApiModelProperty(value = "状态")
    private int status;

    @ApiModelProperty(value = "提示")
    private String message;

    @ApiModelProperty(value = "数据")
    private T data;

    public ReturnModel() {
    }

    public ReturnModel(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ReturnModel(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
