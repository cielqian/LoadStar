package com.ciel.pocket.auth.repository;

import com.ciel.pocket.auth.domain.User;
import com.ciel.pocket.infrastructure.repositories.QueryDslBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends QueryDslBaseRepository<User,String> {
    Optional<User> findUserByUsername(String username);
}
