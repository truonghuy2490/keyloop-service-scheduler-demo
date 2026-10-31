package com.keyloop.scheduler.application.abstraction.dataaccess;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}