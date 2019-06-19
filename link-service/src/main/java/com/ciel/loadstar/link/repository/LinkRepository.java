package com.ciel.loadstar.link.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciel.loadstar.link.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LinkRepository extends BaseMapper<Link> {

    List<Link> queryAllUnderTag(@Param("userId") Long userId, @Param("tagId") Long tagId);

    List<Link> queryAllUnderFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);

    @Select("SELECT * FROM link WHERE ${ew.customSqlSegment}")
    List<Link> queryAll(Page<Link> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    Integer countByUser(@Param("userId") Long userId);

    void deleteByFolder(@Param("folderId") Long folderId);

    void updateFolderById(@Param("linkId") Long linkId, @Param("folderId") Long folderId);
}