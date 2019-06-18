package com.ciel.loadstar.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/1/9 13:22
 */

@Data
public abstract class LogicDeleteEntity {
    @TableLogic
    boolean isDelete;
}
