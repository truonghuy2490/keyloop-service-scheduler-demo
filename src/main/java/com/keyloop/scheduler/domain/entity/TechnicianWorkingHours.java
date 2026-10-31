package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableCreatedEntity;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

public class TechnicianWorkingHours extends SoftDeletableCreatedEntity {
    private UUID technicianId;
    private Short dayOfWeek;
    private LocalTime startsAt;
    private LocalTime endsAt;
    public TechnicianWorkingHours() {
    }
    public UUID getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(UUID technicianId) {
        this.technicianId = technicianId;
    }

    public Short getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Short dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
}
