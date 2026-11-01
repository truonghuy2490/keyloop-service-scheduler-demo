package com.keyloop.scheduler.persistence.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseUuidJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    protected BaseUuidJpaEntity() {
    }

    protected BaseUuidJpaEntity(Builder<?> builder) {
        this.id = builder.id;
    }

    public UUID getId() {
        return id;
    }

    public abstract static class Builder<T extends Builder<T>> {

        protected UUID id;

        public T id(UUID id) {
            this.id = id;
            return self();
        }

        protected abstract T self();
    }
}