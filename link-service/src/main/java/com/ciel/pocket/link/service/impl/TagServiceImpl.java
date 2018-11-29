package com.ciel.pocket.link.service.impl;

import com.ciel.pocket.link.domain.Tag;
import com.ciel.pocket.link.repository.TagRepository;
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
    TagRepository tagRepository;

    @Override
    public Long create(Tag tag) {
        tagRepository.save(tag);
        return tag.getId();
    }

    @Override
    public List<Tag> queryAllTag(Long userId) {
        return tagRepository.findAllByUserId(userId);
    }

    @Override
    public List<Tag> queryAllTag(Long userId, String keyword) {
        return tagRepository.findAllByUserIdAndNameLike(userId, keyword);
    }

    @Override
    public void delete(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}
