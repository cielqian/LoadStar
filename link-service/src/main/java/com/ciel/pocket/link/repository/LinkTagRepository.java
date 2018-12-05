package com.ciel.pocket.link.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/12/5 17:11
 */

@Mapper
public interface LinkTagRepository {
    @Insert("INSERT INTO link_tag VALUES(#{link_id}, #{tag_id})")
    void test(@Param("link_id") Long linkId, @Param("tag_id") Long tagId);
}
