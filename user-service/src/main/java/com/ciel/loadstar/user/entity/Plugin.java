package com.ciel.loadstar.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/22 13:37
 */
@Data
@TableName("plugin")
public class Plugin extends BaseEntity {

    String name;

    String content;

    String icon;

    String code;
}
