package com.ciel.pocket.infrastructure.dto.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
@ApiModel
@Data
public class PageInput {
    private static final int DEFAULT_PAGESIZE_ = 10;

    @ApiModelProperty(value = "排序")
    List<SortModel> sorts;

    @ApiModelProperty(value = "页大小")
    int pageSize;

    @ApiModelProperty(value = "当前页")
    int currentPage;

    public int offSet(){
        int offSet = 0;
        if (currentPage != 0){
            offSet = (currentPage + 1) * pageSize;
        }
        return offSet == 0 ? offSet : (offSet - pageSize);
    }

}
