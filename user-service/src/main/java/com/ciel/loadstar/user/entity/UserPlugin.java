package com.ciel.loadstar.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/22 13:38
 */

@Data
@TableName("user_plugin")
public class UserPlugin extends BaseEntity {

    Long user_id;

    Long plugin_id;
}
