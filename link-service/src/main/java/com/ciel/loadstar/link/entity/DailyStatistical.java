package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/7/11 14:31
 */

@Data
@TableName("daily_statistical")
public class DailyStatistical{
    @TableId
    Long id;

    private Long userId;

    private Date date;

    private Integer visitCount;

    private String type;
}
