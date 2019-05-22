package com.ciel.pocket.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/22 13:38
 */

@Data
@TableName("user_plugin")
public class UserPlugin {
    @TableId(type = IdType.ID_WORKER)
    Long id;

    @TableLogic
    boolean is_delete;

    Long user_id;

    Long plugin_id;
}
