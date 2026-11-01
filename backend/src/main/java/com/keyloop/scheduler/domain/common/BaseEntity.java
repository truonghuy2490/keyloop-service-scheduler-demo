package com.keyloop.scheduler.domain.common;

import java.util.UUID;

public abstract class BaseEntity {

    private UUID id;

    protected BaseEntity() {
    }

    protected BaseEntity(Builder<?> builder) {
        this.id = builder.id;
    }

    public UUID getId() {
        return id;
    }

    public abstract static class Builder<T extends Builder<T>> {

        private UUID id;

        public T id(UUID id) {
            this.id = id;
            return self();
        }

        protected abstract T self();
    }
}