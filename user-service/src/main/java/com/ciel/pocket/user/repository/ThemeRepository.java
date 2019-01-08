package com.ciel.pocket.user.repository;

import com.ciel.pocket.infrastructure.repositories.BaseRepository;
import com.ciel.pocket.user.domain.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends BaseRepository<Theme, Long>, QuerydslPredicateExecutor<T> {
    Theme findByUserId (Long user);
}
