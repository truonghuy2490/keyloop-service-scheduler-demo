package com.keyloop.scheduler.domain.common;

import java.time.Instant;

public abstract class AuditableEntity extends CreatedAtEntity {

    private Instant updatedAt;

    protected AuditableEntity() {
    }

    protected AuditableEntity(Builder<?> builder) {
        super(builder);
        this.updatedAt = builder.updatedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends CreatedAtEntity.Builder<T> {

        private Instant updatedAt;

        public T updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return self();
        }
    }
}