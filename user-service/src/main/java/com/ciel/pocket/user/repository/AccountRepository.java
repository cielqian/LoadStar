package com.ciel.pocket.user.repository;

import com.ciel.pocket.infrastructure.repositories.BaseRepository;
import com.ciel.pocket.user.domain.Account;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

    Account findByUsername(String username);

}
