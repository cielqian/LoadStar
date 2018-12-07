package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.link.mapper.TagMapper;
import com.ciel.pocket.link.model.Tag;
import com.ciel.pocket.link.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:22
 */

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;

    @Override
    public Long create(Tag tag) {
        tagMapper.insert(tag);
        return tag.getId();
    }

    @Override
    public List<Tag> queryAllTag(Long userId) {
        return tagMapper.queryAll(userId);
    }

    @Override
    public List<Tag> queryAllTag(Long userId, String keyword) {
        return tagMapper.queryAllLike(userId, keyword);
    }

    @Override
    public void delete(Long tagId) {
        tagMapper.deleteByPrimaryKey(tagId);
    }

}
