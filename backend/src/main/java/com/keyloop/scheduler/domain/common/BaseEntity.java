package com.keyloop.scheduler.domain.common;

import java.util.UUID;

/** Shared identity for domain entities backed by UUID primary keys. */
public abstract class BaseEntity {
    private UUID id;
    protected BaseEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
}
