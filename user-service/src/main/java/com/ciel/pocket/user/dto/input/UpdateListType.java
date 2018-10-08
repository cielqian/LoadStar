package com.ciel.pocket.user.dto.input;

import com.ciel.pocket.user.infrastructure.enums.ListTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UpdateListType {

    @ApiModelProperty(name = "显示方式")
    private ListTypeEnum listType;
}
