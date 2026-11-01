package com.keyloop.scheduler.persistence.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.Instant;

@MappedSuperclass
public abstract class CreatedAtJpaEntity extends BaseUuidJpaEntity {

    @Column(
        name = "created_at",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private Instant createdAt;

    protected CreatedAtJpaEntity() {
    }

    protected CreatedAtJpaEntity(Builder<?> builder) {
        super(builder);
        this.createdAt = builder.createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends BaseUuidJpaEntity.Builder<T> {

        protected Instant createdAt;

        public T createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return self();
        }
    }
}