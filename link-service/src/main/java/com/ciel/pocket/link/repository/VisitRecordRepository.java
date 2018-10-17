package com.ciel.pocket.link.repository;

import com.ciel.pocket.link.domain.VisitRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/17 16:26
 */
@Repository
public interface VisitRecordRepository extends JpaRepository<VisitRecord, Long> {
}
