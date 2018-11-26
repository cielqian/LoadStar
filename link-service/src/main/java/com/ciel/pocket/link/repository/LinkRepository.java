package com.ciel.pocket.link.repository;

import com.ciel.pocket.infrastructure.repositories.QueryDslBaseRepository;
import com.ciel.pocket.link.domain.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface LinkRepository extends QueryDslBaseRepository<Link, Long>, CrudRepository<Link, Long> {
    List<Link> findAllByUserIdAndIsDeleteEqualsOrderBySortIndex(Long userId, boolean isDelete);

    List<Link> findAllByUserIdAndIsDeleteEqualsAndFolderIdEquals(Long userId, boolean isDelete, Long folderId);

    Integer countByUserId(Long userId);

    Link findByUserIdEqualsAndSortIndexEquals(Long userId, Integer sortIndex);

    @Transactional
    @Modifying
    @Query("update Link l set l.sortIndex = l.sortIndex - 1 where l.userId = ?1 and l.sortIndex > ?2")
    Integer updateSortIndexBatch(Long userId, Integer sortIndex);

    Page<Link> findAllByUserIdAndIsDeleteEquals(Pageable pageable, Long userId, boolean isDelete);

}
