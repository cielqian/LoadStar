package com.ciel.loadstar.link.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.ciel.loadstar.infrastructure.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class VisitRecord extends BaseEntity {

    private Long userId;

    private Long linkId;

}