package com.ciel.loadstar.link.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.link.dto.output.QueryTagListOutput;
import com.ciel.loadstar.link.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagRepository extends BaseMapper<Tag> {
    List<QueryTagListOutput> queryAll(@Param("userId") Long userId);

    List<Tag> queryAllLike(@Param("userId") Long userId, @Param("keyword") String keyword);
}