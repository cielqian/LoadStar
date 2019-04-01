package com.ciel.pocket.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.pocket.user.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserRepository extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);


}
