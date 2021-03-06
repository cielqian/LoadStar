package com.ciel.loadstar.link.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/2/19 14:56
 */

@ApiModel
@Data
public class UpdateLinkInput extends CreateLinkInput{
    @ApiModelProperty(value = "Id")
    private Long id;
}
