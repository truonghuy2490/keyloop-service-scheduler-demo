package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class CreatedAtJpaEntity extends BaseUuidJpaEntity {
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;
    protected CreatedAtJpaEntity() {}
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
