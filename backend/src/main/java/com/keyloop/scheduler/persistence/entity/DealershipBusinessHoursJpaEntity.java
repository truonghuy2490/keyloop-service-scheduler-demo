package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import java.time.LocalTime;

@Entity
@Table(name = "dealership_business_hours")
public class DealershipBusinessHoursJpaEntity extends SoftDeletedCreatedJpaEntity {
    @Column(name = "dealership_id", nullable = false)
    private UUID dealershipId;

    @Column(name = "day_of_week", nullable = false)
    private Short dayOfWeek;

    @Column(name = "is_closed", nullable = false)
    private boolean closed;

    @Column(name = "opens_at")
    private LocalTime opensAt;

    @Column(name = "closes_at")
    private LocalTime closesAt;

    public DealershipBusinessHoursJpaEntity() {}

    public UUID getDealershipId() { return dealershipId; }
    public void setDealershipId(UUID dealershipId) { this.dealershipId = dealershipId; }

    public Short getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Short dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public boolean isClosed() { return closed; }
    public void setClosed(boolean closed) { this.closed = closed; }

    public LocalTime getOpensAt() { return opensAt; }
    public void setOpensAt(LocalTime opensAt) { this.opensAt = opensAt; }

    public LocalTime getClosesAt() { return closesAt; }
    public void setClosesAt(LocalTime closesAt) { this.closesAt = closesAt; }

}
