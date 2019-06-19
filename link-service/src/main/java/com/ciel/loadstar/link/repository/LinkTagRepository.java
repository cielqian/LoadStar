package com.ciel.loadstar.link.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.link.entity.LinkTag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LinkTagRepository extends BaseMapper<LinkTag> {
//    @Delete("DELETE * FROM LinkTag WHERE ${ew.customSqlSegment}")
//    int delete(@Param(Constants.WRAPPER) Wrapper wrapper);
}