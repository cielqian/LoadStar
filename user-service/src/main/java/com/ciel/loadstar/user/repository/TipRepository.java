package com.ciel.loadstar.user.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.user.entity.Tip;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/16 19:36
 */
@Repository
public interface TipRepository extends BaseMapper<Tip> {

    @Select("SELECT * FROM tip WHERE ${ew.sqlSegment}")
    List<Tip> selectAllTip(@Param("ew") Wrapper<Tip> queryWrapper);
}
