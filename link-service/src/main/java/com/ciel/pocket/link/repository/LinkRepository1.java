package com.ciel.pocket.link.repository;

import com.ciel.pocket.link.domain.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/12/5 17:24
 */
@Mapper
public interface LinkRepository1 {

    @Query("SELECT l.* " +
            "FROM link_tag AS lt " +
            "LEFT JOIN link AS l " +
            "ON lt.link_id = l.id " +
            "WHERE l.is_delete = 0 " +
            "AND l.user_id = #{userId} " +
            "AND lt.tag_id = #{tagId}")
    List<Link> findAllByUserIdAndIsDeleteEqualsAndTagIdEquals(@Param("userId") Long userId, @Param("{tagId}") Long tagId);

}
