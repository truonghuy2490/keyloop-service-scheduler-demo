package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

import java.util.UUID;

public class ServiceBay extends SoftDeletableAuditableEntity {
    private UUID dealershipId;
    private String bayCode;
    private boolean active;
    public ServiceBay() {
    }
    public UUID getDealershipId() {
        return dealershipId;
    }

    public void setDealershipId(UUID dealershipId) {
        this.dealershipId = dealershipId;
    }

    public String getBayCode() {
        return bayCode;
    }

    public void setBayCode(String bayCode) {
        this.bayCode = bayCode;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
