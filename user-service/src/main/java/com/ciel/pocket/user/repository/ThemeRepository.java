package com.ciel.pocket.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.pocket.user.domain.Theme;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends BaseMapper<Theme>{

    @Select("SELECT * FROM theme WHERE userId = #{userId}")
    Theme findByUserId (@Param("userId")Long user);
}
