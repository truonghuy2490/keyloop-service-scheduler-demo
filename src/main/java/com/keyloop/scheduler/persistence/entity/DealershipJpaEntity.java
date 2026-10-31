package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "dealership")
public class DealershipJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "time_zone", nullable = false, length = 64)
    private String timeZone;

    public DealershipJpaEntity() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTimeZone() { return timeZone; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }

}
