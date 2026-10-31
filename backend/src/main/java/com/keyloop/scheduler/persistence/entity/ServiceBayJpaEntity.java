package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "service_bay")
public class ServiceBayJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "dealership_id", nullable = false)
    private UUID dealershipId;

    @Column(name = "bay_code", nullable = false, length = 40)
    private String bayCode;

    @Column(name = "active", nullable = false)
    private boolean active;

    public ServiceBayJpaEntity() {}

    public UUID getDealershipId() { return dealershipId; }
    public void setDealershipId(UUID dealershipId) { this.dealershipId = dealershipId; }

    public String getBayCode() { return bayCode; }
    public void setBayCode(String bayCode) { this.bayCode = bayCode; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

}
