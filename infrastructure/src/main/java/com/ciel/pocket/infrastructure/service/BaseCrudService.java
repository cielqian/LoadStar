package com.ciel.pocket.infrastructure.service;

import com.ciel.pocket.infrastructure.domain.BaseEntity;
import com.ciel.pocket.infrastructure.dto.web.PageInput;
import com.ciel.pocket.infrastructure.dto.web.PageOutput;
import com.ciel.pocket.infrastructure.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @Author cielqian
 * @Email qianhong91@outlook.com
 * @CreateTime 2018/10/9 05:42
 * @Comment
 */

public abstract class BaseCrudService<T extends BaseEntity, ID extends Serializable> implements ICrudService<T, ID>{
    @Autowired
    protected BaseRepository<T, ID> repository;

    @Override
    public T save(T element) {
        return repository.save(element);
    }

    @Override
    public boolean exists(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<T> findOne(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return (List<T>)repository.findAll();
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(T element) {
        repository.delete(element);
    }

    @Override
    public PageOutput<T> findPaged(PageInput pageInput) {
        PageRequest pageRequest = PageRequest.of(pageInput.getCurrentPage(), pageInput.getPageSize());
        Page<T> result = repository.findAll(pageRequest);
        return new PageOutput<T>(result.getContent(), result.getTotalElements(), result.getTotalPages());
    }
}
