package com.keyloop.scheduler.domain.common;

public abstract class SoftDeletableAuditableEntity extends AuditableEntity {

    private boolean deleted;

    protected SoftDeletableAuditableEntity() {
    }

    protected SoftDeletableAuditableEntity(Builder<?> builder) {
        super(builder);
        this.deleted = builder.deleted;
    }

    public boolean getIsDeleted() {
        return deleted;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends AuditableEntity.Builder<T> {

        private boolean deleted;

        public T isDeleted(boolean deleted) {
            this.deleted = deleted;
            return self();
        }
    }
}