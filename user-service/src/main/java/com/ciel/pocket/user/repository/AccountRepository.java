package com.ciel.pocket.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.pocket.user.domain.User;
import org.springframework.stereotype.Repository;


public interface AccountRepository extends BaseMapper<User> {

    User findByUsername(String username);

}
