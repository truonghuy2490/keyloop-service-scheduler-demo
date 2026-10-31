package com.keyloop.scheduler.domain.common;

import java.time.Instant;

public abstract class CreatedAtEntity extends BaseEntity {
    private Instant createdAt;
    protected CreatedAtEntity() {}
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
