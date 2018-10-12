package com.ciel.pocket.link.repository;

import com.ciel.pocket.link.domain.Link;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    List<Link> findAllByUserIdOrderBySortIndex(Long userId);

    Integer countByUserId(Long userId);

    Link findByUserIdEqualsAndSortIndexEquals(Long userId, Integer sortIndex);

    @Transactional
    @Modifying
    @Query("update Link l set l.sortIndex = l.sortIndex - 1 where l.userId = ?1 and l.sortIndex > ?2")
    Integer updateSortIndexBatch(Long userId, Integer sortIndex);
}
