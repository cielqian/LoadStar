package com.ciel.pocket.link.service;

import com.ciel.pocket.link.domain.Folder;
import com.ciel.pocket.link.dto.output.FolderTreeOutput;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 13:27
 */

public interface FolderService {
    Long create(Folder folder);

    List<FolderTreeOutput> queryFolderTree(Long userId);
}
