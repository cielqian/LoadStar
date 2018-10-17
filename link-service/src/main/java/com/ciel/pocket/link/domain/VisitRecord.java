package com.ciel.pocket.link.domain;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/17 15:59
 */

@Entity
@Data
public class VisitRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linkId")
    Link link;

    Long userId;
}
