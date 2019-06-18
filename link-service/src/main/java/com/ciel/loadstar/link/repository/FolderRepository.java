package com.ciel.loadstar.link.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.link.entity.Folder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FolderRepository extends BaseMapper<Folder> {
    List<Folder> queryAll(@Param("userId") Long userId);

    List<Folder> queryAllUnderFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);

    Folder queryFolderByCode(@Param("userId") Long userId, @Param("code") String code);
}