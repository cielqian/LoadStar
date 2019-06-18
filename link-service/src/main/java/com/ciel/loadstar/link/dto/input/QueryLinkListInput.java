package com.ciel.loadstar.link.dto.input;

import com.ciel.loadstar.infrastructure.dto.web.PageInput;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/1/4 14:57
 */

@Data
public class QueryLinkListInput extends PageInput {
    private String keyword;
}
