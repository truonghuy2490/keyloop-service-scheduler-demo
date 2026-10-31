package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "technician_date_override")
public class TechnicianDateOverrideJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "technician_id", nullable = false)
    private UUID technicianId;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "is_unavailable", nullable = false)
    private boolean unavailable;

    @Column(name = "starts_at")
    private LocalTime startsAt;

    @Column(name = "ends_at")
    private LocalTime endsAt;

    @Column(name = "reason", length = 300)
    private String reason;

    public TechnicianDateOverrideJpaEntity() {}

    public UUID getTechnicianId() { return technicianId; }
    public void setTechnicianId(UUID technicianId) { this.technicianId = technicianId; }

    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }

    public boolean isUnavailable() { return unavailable; }
    public void setUnavailable(boolean unavailable) { this.unavailable = unavailable; }

    public LocalTime getStartsAt() { return startsAt; }
    public void setStartsAt(LocalTime startsAt) { this.startsAt = startsAt; }

    public LocalTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalTime endsAt) { this.endsAt = endsAt; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

}
