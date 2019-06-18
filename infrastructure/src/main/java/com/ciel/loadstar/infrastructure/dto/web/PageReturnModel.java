package com.ciel.loadstar.infrastructure.dto.web;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
@Data
@ApiModel
public class PageReturnModel<T> extends ReturnModel<PageOutput<T>> {
}
