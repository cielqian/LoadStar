package com.ciel.loadstar.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.user.entity.Theme;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends BaseMapper<Theme>{

    @Select("SELECT * FROM theme WHERE user_id = #{userId}")
    Theme findByUserId (@Param("userId")Long user);
}
