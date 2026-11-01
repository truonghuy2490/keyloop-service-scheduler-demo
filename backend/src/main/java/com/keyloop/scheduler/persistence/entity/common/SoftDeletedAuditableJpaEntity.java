package com.keyloop.scheduler.persistence.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeletedAuditableJpaEntity
        extends CreatedUpdatedAtJpaEntity {

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    protected SoftDeletedAuditableJpaEntity() {
    }

    protected SoftDeletedAuditableJpaEntity(Builder<?> builder) {
        super(builder);
        this.deleted = builder.deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends CreatedUpdatedAtJpaEntity.Builder<T> {

        protected boolean deleted;

        public T deleted(boolean deleted) {
            this.deleted = deleted;
            return self();
        }
    }
}