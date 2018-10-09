package com.ciel.pocket.user.dto.input;

import com.ciel.pocket.infrastructure.enums.Language;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/9 12:56
 */

@ApiModel
@Data
public class UpdateLanguage {
    @ApiModelProperty(name = "语言")
    private Language language;
}
