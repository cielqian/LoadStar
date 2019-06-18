package com.ciel.loadstar.link.dto.input;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 14:11
 */
@ApiModel
@Data
public class CreateFolderInput {
    private String name;

    private Long parentId;
}
