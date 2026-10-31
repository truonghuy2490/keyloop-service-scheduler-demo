package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class CreatedUpdatedAtJpaEntity extends CreatedAtJpaEntity {
    @Column(name = "updated_at", nullable = false, insertable = false)
    private Instant updatedAt;
    protected CreatedUpdatedAtJpaEntity() {}
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
