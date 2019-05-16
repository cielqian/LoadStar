package com.ciel.pocket.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.pocket.user.domain.Theme;
import com.ciel.pocket.user.domain.Tip;
import org.springframework.stereotype.Repository;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/5/16 19:36
 */
@Repository
public interface TipRepository extends BaseMapper<Tip> {
}
