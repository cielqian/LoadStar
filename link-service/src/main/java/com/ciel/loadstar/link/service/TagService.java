package com.ciel.loadstar.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.loadstar.link.dto.output.QueryTagListOutput;
import com.ciel.loadstar.link.model.Tag;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:21
 */

public interface TagService extends IService<Tag> {
    Long create(Tag tag);

    List<QueryTagListOutput> queryAllTag(Long userId);

    List<Tag> queryAllTag(Long userId, String keyword);

    void delete(Long tagId);

}
