package com.ciel.pocket.link.repository;

import com.ciel.pocket.infrastructure.repositories.QueryDslBaseRepository;
import com.ciel.pocket.link.domain.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/11/23 11:23
 */
@Repository
public interface TagRepository  extends QueryDslBaseRepository<Tag, Long> {
    List<Tag> findAllByUserId(Long userId);

    List<Tag> findAllByUserIdAndNameLike(Long userId, String name);
}
