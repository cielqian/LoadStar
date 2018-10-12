package com.ciel.pocket.link.repository;

import com.ciel.pocket.link.domain.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    List<Link> findAllByUserId(Long userId);
}
