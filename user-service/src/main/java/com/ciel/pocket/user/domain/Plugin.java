package com.ciel.pocket.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/22 13:37
 */
@Data
@TableName("plugin")
public class Plugin {
    @TableId(type = IdType.ID_WORKER)
    Long id;

    @TableLogic
    boolean is_delete;

    String name;

    String content;

    String icon;

    String code;
}
