package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeletedAuditableJpaEntity extends CreatedUpdatedAtJpaEntity {
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;
    protected SoftDeletedAuditableJpaEntity() {}
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
