package com.ciel.pocket.user.repository;

import com.ciel.pocket.infrastructure.repositories.BaseRepository;
import com.ciel.pocket.infrastructure.repositories.QueryDslBaseRepository;
import com.ciel.pocket.user.domain.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ThemeRepository extends QueryDslBaseRepository<Theme, Long> {
    Theme findByUserId (Long user);
}
