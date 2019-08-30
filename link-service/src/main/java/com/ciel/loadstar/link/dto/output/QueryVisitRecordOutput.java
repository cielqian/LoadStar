package com.ciel.loadstar.link.dto.output;

import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/7/11 16:52
 */
@Data
public class QueryVisitRecordOutput {
    private String title;

    private Date visitTime;

    private Long linkId;
}
