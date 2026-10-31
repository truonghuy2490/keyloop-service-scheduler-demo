package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TechnicianDateOverride extends SoftDeletableAuditableEntity {
    private UUID technicianId;
    private LocalDate workDate;
    private boolean isUnavailable;
    private LocalTime startsAt;
    private LocalTime endsAt;
    private String reason;
    public TechnicianDateOverride() {
    }
    public UUID getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(UUID technicianId) {
        this.technicianId = technicianId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public boolean getIsUnavailable() {
        return isUnavailable;
    }

    public void setIsUnavailable(boolean isUnavailable) {
        this.isUnavailable = isUnavailable;
    }

    public LocalTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalTime endsAt) {
        this.endsAt = endsAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
