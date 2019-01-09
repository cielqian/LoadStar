package com.ciel.pocket.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.pocket.user.domain.Theme;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends BaseMapper<Theme>{
    Theme findByUserId (Long user);
}
