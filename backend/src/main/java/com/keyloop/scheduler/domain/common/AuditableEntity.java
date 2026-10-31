package com.keyloop.scheduler.domain.common;

import java.time.Instant;

/** Domain base for records that track creation and last update time. */
public abstract class AuditableEntity extends CreatedAtEntity {
    private Instant updatedAt;
    protected AuditableEntity() {}
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
