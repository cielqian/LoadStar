package com.ciel.pocket.user.repository;

import com.ciel.pocket.infrastructure.repositories.QueryDslBaseRepository;
import com.ciel.pocket.user.domain.User;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends QueryDslBaseRepository<User, Long> {

    User findByUsername(String username);


}
