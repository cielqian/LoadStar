package com.ciel.pocket.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 19:26
 */
@NoRepositoryBean
public interface QueryDslBaseRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
}
