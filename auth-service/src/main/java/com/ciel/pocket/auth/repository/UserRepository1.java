package com.ciel.pocket.auth.repository;

import com.ciel.pocket.auth.domain.User;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository1 extends CrudRepository<User,String> {
    Optional<User> findUserByUsername(String username);
}
