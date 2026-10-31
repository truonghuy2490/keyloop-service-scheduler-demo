package com.keyloop.scheduler.domain.common;

public abstract class SoftDeletableAuditableEntity extends AuditableEntity {
    private boolean deleted;
    protected SoftDeletableAuditableEntity() {}
    public boolean getIsDeleted() { return deleted; }
    public void setIsDeleted(boolean deleted) { this.deleted = deleted; }
}
