package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeletedCreatedJpaEntity extends CreatedAtJpaEntity {
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;
    protected SoftDeletedCreatedJpaEntity() {}
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
