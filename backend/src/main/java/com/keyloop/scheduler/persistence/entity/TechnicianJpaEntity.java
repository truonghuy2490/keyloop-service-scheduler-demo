package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

import com.keyloop.scheduler.persistence.entity.common.SoftDeletedAuditableJpaEntity;

@Entity
@Table(name = "technician")
public class TechnicianJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "dealership_id", nullable = false)
    private UUID dealershipId;

    @Column(name = "full_name", nullable = false, length = 160)
    private String fullName;

    @Column(name = "active", nullable = false)
    private boolean active;

    public TechnicianJpaEntity() {}

    public UUID getDealershipId() { return dealershipId; }
    public void setDealershipId(UUID dealershipId) { this.dealershipId = dealershipId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

}
