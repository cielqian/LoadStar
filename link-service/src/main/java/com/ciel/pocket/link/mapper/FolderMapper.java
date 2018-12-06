package com.ciel.pocket.link.mapper;

import com.ciel.pocket.link.infrastructure.mapper.MyMapper;
import com.ciel.pocket.link.model.Folder;

import java.util.List;

public interface FolderMapper extends MyMapper<Folder> {
    List<Folder> queryAllByUserIdAndIsDeleteEquals();
}