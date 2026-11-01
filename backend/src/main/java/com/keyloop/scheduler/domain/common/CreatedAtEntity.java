package com.keyloop.scheduler.domain.common;

import java.time.Instant;

public abstract class CreatedAtEntity extends BaseEntity {

    private Instant createdAt;

    protected CreatedAtEntity() {
    }

    protected CreatedAtEntity(Builder<?> builder) {
        super(builder);
        this.createdAt = builder.createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends BaseEntity.Builder<T> {

        private Instant createdAt;

        public T createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return self();
        }
    }
}