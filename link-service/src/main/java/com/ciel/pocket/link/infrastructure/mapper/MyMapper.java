package com.ciel.pocket.link.infrastructure.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/12/6 15:50
 */

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
