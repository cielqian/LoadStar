package com.ciel.loadstar.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.loadstar.user.entity.AlarmClock;
import com.ciel.loadstar.user.entity.Passbook;
import org.springframework.stereotype.Repository;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/26 15:57
 */
@Repository
public interface AlarmClockRepository  extends BaseMapper<AlarmClock> {
}
