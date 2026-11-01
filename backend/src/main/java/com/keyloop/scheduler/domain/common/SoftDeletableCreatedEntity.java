package com.keyloop.scheduler.domain.common;

public abstract class SoftDeletableCreatedEntity extends CreatedAtEntity {

    private boolean deleted;

    protected SoftDeletableCreatedEntity() {
    }

    protected SoftDeletableCreatedEntity(Builder<?> builder) {
        super(builder);
        this.deleted = builder.deleted;
    }

    public boolean getIsDeleted() {
        return deleted;
    }

    public abstract static class Builder<T extends Builder<T>>
            extends CreatedAtEntity.Builder<T> {

        private boolean deleted;

        public T isDeleted(boolean deleted) {
            this.deleted = deleted;
            return self();
        }
    }
}