package com.ciel.pocket.infrastructure.service;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import com.ciel.pocket.infrastructure.dto.web.PageInput;
import com.ciel.pocket.infrastructure.dto.web.PageOutput;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2018/10/9 05:42
 * @Comment
 */

public interface ICrudService<T extends BaseEntity, ID extends Serializable> {
    T save(T element);

    void delete(T element);

    void delete(ID id);

    Optional<T> findOne(ID id);

    List<T> findAll();

    boolean exists(ID id);

    PageOutput<T> findPaged(PageInput pageInput);
}
