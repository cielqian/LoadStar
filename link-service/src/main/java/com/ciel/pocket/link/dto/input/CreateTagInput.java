package com.ciel.pocket.link.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:27
 */

@ApiModel
@Data
public class CreateTagInput {
    @ApiModelProperty(value = "名称")
    private String name;
}
