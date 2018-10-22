package com.ciel.pocket.link.domain;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/22 13:24
 */

@Data
@Entity
public class Folder extends BaseEntity {

    private Long userId;

    private String name;

    private String code;

    private Integer deep;

    private Long parentId;

    private Integer sortIndex;

    private boolean isSystem;

    @OneToMany(mappedBy = "folder")
    private List<Link> links;
}
