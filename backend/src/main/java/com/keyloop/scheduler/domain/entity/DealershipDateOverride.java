package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class DealershipDateOverride extends SoftDeletableAuditableEntity {
    private UUID dealershipId;
    private LocalDate businessDate;
    private boolean isClosed;
    private LocalTime opensAt;
    private LocalTime closesAt;
    private String reason;
    public DealershipDateOverride() {
    }
    public UUID getDealershipId() {
        return dealershipId;
    }

    public void setDealershipId(UUID dealershipId) {
        this.dealershipId = dealershipId;
    }

    public LocalDate getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(LocalDate businessDate) {
        this.businessDate = businessDate;
    }

    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public LocalTime getOpensAt() {
        return opensAt;
    }

    public void setOpensAt(LocalTime opensAt) {
        this.opensAt = opensAt;
    }

    public LocalTime getClosesAt() {
        return closesAt;
    }

    public void setClosesAt(LocalTime closesAt) {
        this.closesAt = closesAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
