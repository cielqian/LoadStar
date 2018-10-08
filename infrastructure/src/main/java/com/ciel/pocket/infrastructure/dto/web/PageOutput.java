package com.ciel.pocket.infrastructure.dto.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
@Data
@NoArgsConstructor
public class PageOutput<T> {
    @ApiModelProperty(value = "总数")
    long total;

    @ApiModelProperty(value = "页大小")
    int pageSize;

    @ApiModelProperty(value = "当前页")
    int currentPage;

    @ApiModelProperty(value = "总页数")
    long totalPage;

    @ApiModelProperty(value = "数据")
    List<T> items;

    public PageOutput(List<T> items, long total, long totalPage) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
