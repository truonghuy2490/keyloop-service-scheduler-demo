package com.keyloop.scheduler.persistence.adapter;


import org.springframework.data.jpa.repository.JpaRepository;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;

import java.util.List;
import java.util.Optional;


public abstract class BaseRepositoryAdapter<D, E, ID>
        implements BaseRepository<D, ID> {

    protected final JpaRepository<E, ID> jpaRepository;

    protected BaseRepositoryAdapter(
            JpaRepository<E, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    protected abstract D toDomain(E entity);

    protected abstract E toPersistence(D domain);

    @Override
    public Optional<D> findById(ID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<D> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public D save(D domain) {
        E entity = toPersistence(domain);

        E savedEntity = jpaRepository.save(entity);

        return toDomain(savedEntity);
    }

    @Override
    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return jpaRepository.existsById(id);
    }
}
