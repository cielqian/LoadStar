package com.ciel.loadstar.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserRepository extends BaseMapper<User> {
}
