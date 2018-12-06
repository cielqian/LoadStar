package com.ciel.pocket.link.mapper;

import com.ciel.pocket.link.infrastructure.mapper.MyMapper;
import com.ciel.pocket.link.model.Link;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkMapper extends MyMapper<Link> {

    List<Link> findAllByUserIdAndIsDeleteEqualsAndTagIdEquals(@Param("userId") Long userId, @Param("tagId") Long tagId);
}