package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

import java.util.UUID;

public class Technician extends SoftDeletableAuditableEntity {
    private UUID dealershipId;
    private String fullName;
    private boolean active;
    public Technician() {
    }
    public UUID getDealershipId() {
        return dealershipId;
    }

    public void setDealershipId(UUID dealershipId) {
        this.dealershipId = dealershipId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
