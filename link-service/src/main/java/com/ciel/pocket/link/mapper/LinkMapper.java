package com.ciel.pocket.link.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ciel.pocket.link.model.Link;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LinkMapper extends BaseMapper<Link> {

    List<Link> queryAllUnderTag(@Param("userId") Long userId, @Param("tagId") Long tagId);

    List<Link> queryAllUnderFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);

    @Select("SELECT * FROM link WHERE ${ew.customSqlSegment}")
    List<Link> queryAll(Page<Link> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    Integer countByUser(@Param("userId") Long userId);

    void deleteById(@Param("linkId") Long linkId);

    void deleteByFolder(@Param("folderId") Long folderId);

    void updateFolderById(@Param("linkId") Long linkId, @Param("folderId") Long folderId);
}