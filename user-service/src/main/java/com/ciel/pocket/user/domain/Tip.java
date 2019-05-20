package com.ciel.pocket.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/16 19:34
 */
@Data
@TableName("tip")
public class Tip {
    @TableId(type = IdType.ID_WORKER)
    Long id;

    @TableLogic
    boolean is_delete;

    Long userId;

    String tip;

    boolean hasRead;

    Date readTime;
}
