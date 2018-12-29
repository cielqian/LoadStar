package com.ciel.pocket.link.mapper;

import com.ciel.pocket.link.infrastructure.mapper.MyMapper;
import com.ciel.pocket.link.model.Link;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkMapper extends MyMapper<Link> {

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Link link);

    List<Link> queryAllUnderTag(@Param("userId") Long userId, @Param("tagId") Long tagId);

    List<Link> queryAllUnderFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);

    List<Link> queryAll(@Param("userId") Long userId);

    Integer countByUser(@Param("userId") Long userId);

    void deleteById(@Param("linkId") Long linkId);

    void deleteByFolder(@Param("folderId") Long folderId);

    void updateFolderById(@Param("linkId") Long linkId, @Param("folderId") Long folderId);
}