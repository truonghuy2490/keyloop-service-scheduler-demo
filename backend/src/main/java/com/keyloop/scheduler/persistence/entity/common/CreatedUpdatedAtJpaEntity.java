package com.keyloop.scheduler.persistence.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.Instant;

@MappedSuperclass
public abstract class CreatedUpdatedAtJpaEntity
        extends CreatedAtJpaEntity {

    @Column(name = "updated_at", nullable = false, insertable = false)
    private Instant updatedAt;

    protected CreatedUpdatedAtJpaEntity() {
    }

    protected CreatedUpdatedAtJpaEntity(Builder<?> builder) {
        super(builder);
        this.updatedAt = builder.updatedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends CreatedAtJpaEntity.Builder<T> {

        protected Instant updatedAt;

        public T updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return self();
        }
    }
}