package com.keyloop.scheduler.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;
import com.keyloop.scheduler.application.common.pagination.PagedList;

public abstract class BaseRepositoryAdapter<D, E, ID>
        implements BaseRepository<D, ID> {

    protected final JpaRepository<E, ID> jpaRepository;

    protected BaseRepositoryAdapter(JpaRepository<E, ID> jpaRepository) {
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
    public PagedList<D> findAll(int pageNumber, int pageSize) {

        if (pageNumber < 1) {
            throw new IllegalArgumentException(
                    "Page number must be greater than 0"
            );
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException(
                    "Page size must be greater than 0"
            );
        }

        Pageable pageable = PageRequest.of(
                pageNumber - 1,
                pageSize
        );

        Page<E> page = jpaRepository.findAll(pageable);

        List<D> items = page.getContent()
                .stream()
                .map(this::toDomain)
                .toList();

        return PagedList.create(
                items,
                page.getTotalElements(),
                pageNumber,
                pageSize
        );
    }

    @Override
    public D save(D domain) {

        if (domain == null) {
            throw new IllegalArgumentException(
                    "Domain entity cannot be null"
            );
        }

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