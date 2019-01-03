package com.ciel.pocket.link.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.pocket.link.dto.output.QueryTagListOutput;
import com.ciel.pocket.link.mapper.TagMapper;
import com.ciel.pocket.link.model.Tag;
import com.ciel.pocket.link.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:22
 */

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public Long create(Tag tag) {
        baseMapper.insert(tag);
        return tag.getId();
    }

    @Override
    public List<QueryTagListOutput> queryAllTag(Long userId) {
        return baseMapper.queryAll(userId);
    }

    @Override
    public List<Tag> queryAllTag(Long userId, String keyword) {
        return baseMapper.queryAllLike(userId, keyword);
    }

    @Override
    public void delete(Long tagId) {
        baseMapper.deleteById(tagId);
    }

}
