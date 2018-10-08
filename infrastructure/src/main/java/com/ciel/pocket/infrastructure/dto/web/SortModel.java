package com.ciel.pocket.infrastructure.dto.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
@Data
public class SortModel {

    @ApiModelProperty(value = "排序字段")
    String column;

    @ApiModelProperty(value = "排序方式", example = "DESC ASC")
    String type;
}
