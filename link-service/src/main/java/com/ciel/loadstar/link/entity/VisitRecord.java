package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("visit_record")
public class VisitRecord extends BaseEntity {

    private Long userId;

    private Long linkId;

}