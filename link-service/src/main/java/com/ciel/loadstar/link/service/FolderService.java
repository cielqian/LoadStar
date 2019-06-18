package com.ciel.loadstar.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.link.dto.output.FolderTreeOutput;
import com.ciel.loadstar.link.entity.Folder;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 13:27
 */

public interface FolderService extends IService<Folder> {
    Long create(Folder folder);

    List<FolderTreeOutput> queryFolderTree(Long userId);

    List<FolderTreeOutput> queryFolderTree(Long folderId, Long userId);
}
