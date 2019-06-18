package com.ciel.loadstar.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.auth.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserRepository extends BaseMapper<User> {

    @Select("SELECT * FROM account WHERE username = #{username}")
    User findUserByUsername(@Param("username") String username);
}
