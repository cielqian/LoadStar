package com.ciel.loadstar.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/18 16:36
 */
@Data
public abstract class BaseEntity extends AuditMetaEntity {
    @TableId
    Long id;
}
