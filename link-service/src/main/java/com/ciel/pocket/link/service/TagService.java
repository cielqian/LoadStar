package com.ciel.pocket.link.service;

import com.ciel.pocket.link.domain.Folder;
import com.ciel.pocket.link.domain.Tag;
import com.ciel.pocket.link.dto.output.FolderTreeOutput;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:21
 */

public interface TagService {
    Long create(Tag tag);

    List<Tag> queryAllTag(Long userId);

    void delete(Long tagId);
}
