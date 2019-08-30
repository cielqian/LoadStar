package com.ciel.loadstar.link.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ciel.loadstar.link.dto.output.QueryVisitRecordOutput;
import com.ciel.loadstar.link.entity.VisitRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface VisitRecordRepository extends BaseMapper<VisitRecord> {

    @Select("SELECT v.`create_time` AS visitTime, l.`title` AS title,l.`id` AS linkId " +
            "FROM `visit_record` AS v " +
            "LEFT JOIN link AS l " +
            "ON v.`link_id` = l.`id` " +
            "WHERE v.user_id = ${userId} AND v.create_time BETWEEN ${fromDate} AND ${toDate}")
    List<QueryVisitRecordOutput> queryVisitRecords(@Param("ew") Wrapper<QueryVisitRecordOutput> queryWrapper);
}