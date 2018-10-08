package com.ciel.pocket.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 11:35
 */

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {
}
