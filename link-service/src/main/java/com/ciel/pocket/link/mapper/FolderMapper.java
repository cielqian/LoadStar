package com.ciel.pocket.link.mapper;

import com.ciel.pocket.link.infrastructure.mapper.MyMapper;
import com.ciel.pocket.link.model.Folder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FolderMapper extends MyMapper<Folder> {
    List<Folder> queryAll(@Param("userId") Long userId);

    List<Folder> queryAllUnderFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);

    Folder queryFolderByCode(@Param("userId") Long userId, @Param("code") String code);
}