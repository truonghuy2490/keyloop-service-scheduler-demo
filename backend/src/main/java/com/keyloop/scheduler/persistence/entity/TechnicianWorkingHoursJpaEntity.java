package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import java.time.LocalTime;

@Entity
@Table(name = "technician_working_hours")
public class TechnicianWorkingHoursJpaEntity extends SoftDeletedCreatedJpaEntity {
    @Column(name = "technician_id", nullable = false)
    private UUID technicianId;

    @Column(name = "day_of_week", nullable = false)
    private Short dayOfWeek;

    @Column(name = "starts_at", nullable = false)
    private LocalTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalTime endsAt;

    public TechnicianWorkingHoursJpaEntity() {}

    public UUID getTechnicianId() { return technicianId; }
    public void setTechnicianId(UUID technicianId) { this.technicianId = technicianId; }

    public Short getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Short dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartsAt() { return startsAt; }
    public void setStartsAt(LocalTime startsAt) { this.startsAt = startsAt; }

    public LocalTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalTime endsAt) { this.endsAt = endsAt; }

}
