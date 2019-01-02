package com.ciel.pocket.link.service;

import com.baomidou.mybatisplus.service.IService;
import com.ciel.pocket.link.dto.output.FolderTreeOutput;
import com.ciel.pocket.link.model.Folder;

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
