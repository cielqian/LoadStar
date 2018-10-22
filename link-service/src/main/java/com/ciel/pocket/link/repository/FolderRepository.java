package com.ciel.pocket.link.repository;

import com.ciel.pocket.infrastructure.repositories.QueryDslBaseRepository;
import com.ciel.pocket.link.domain.Folder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 13:26
 */
@Repository
public interface FolderRepository extends QueryDslBaseRepository<Folder, Long> {

    List<Folder> queryAllByUserIdAndIsDeleteEquals(Long userId, boolean isDelete);

    Folder findFirstByUserIdAndCodeEquals(Long userId, String code);
}
