package com.ciel.pocket.link.mapper;

import com.ciel.pocket.link.infrastructure.mapper.MyMapper;
import com.ciel.pocket.link.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends MyMapper<Tag> {
    List<Tag> queryAll(@Param("userId") Long userId);

    List<Tag> queryAllLike(@Param("userId") Long userId, @Param("keyword") String keyword);
}