package com.keyloop.scheduler.domain.common;

public abstract class SoftDeletableCreatedEntity extends CreatedAtEntity {
    private boolean deleted;
    protected SoftDeletableCreatedEntity() {}
    public boolean getIsDeleted() { return deleted; }
    public void setIsDeleted(boolean deleted) { this.deleted = deleted; }
}
