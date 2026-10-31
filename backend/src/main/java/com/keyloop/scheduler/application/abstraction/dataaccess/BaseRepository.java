package com.keyloop.scheduler.application.abstraction.dataaccess;

import java.util.List;
import java.util.Optional;

import com.keyloop.scheduler.application.common.pagination.PagedList;

public interface BaseRepository<D, ID> {

    Optional<D> findById(ID id);

    List<D> findAll();

    PagedList<D> findAll(int pageNumber, int pageSize);

    D save(D domain);

    void deleteById(ID id);

    boolean existsById(ID id);
}