package com.ciel.loadstar.link.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.link.entity.VisitRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitRecordRepository extends BaseMapper<VisitRecord> {
}