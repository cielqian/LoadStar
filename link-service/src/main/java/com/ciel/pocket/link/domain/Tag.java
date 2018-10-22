package com.ciel.pocket.link.domain;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 16:09
 */
@Entity
@Data
public class Tag extends BaseEntity {
    private Long userId;

    private String name;

    private String code;

    private Integer sortIndex;

    private boolean isSystem;

    @ManyToMany
    @JoinTable(name="link_tag",joinColumns={@JoinColumn(name="link_id")},inverseJoinColumns={@JoinColumn(name="tag_id")})
    private Set<Link> links;
}
