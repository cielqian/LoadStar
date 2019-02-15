package com.ciel.pocket.link.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ciel.pocket.link.model.LinkTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface LinkTagMapper extends BaseMapper<LinkTag> {
    @Delete("DELETE * FROM LinkTag WHERE ${ew.customSqlSegment}")
    int delete(@Param(Constants.WRAPPER) Wrapper wrapper);
}