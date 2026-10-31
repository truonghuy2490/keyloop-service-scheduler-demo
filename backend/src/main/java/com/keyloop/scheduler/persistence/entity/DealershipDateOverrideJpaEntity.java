package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "dealership_date_override")
public class DealershipDateOverrideJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "dealership_id", nullable = false)
    private UUID dealershipId;

    @Column(name = "business_date", nullable = false)
    private LocalDate businessDate;

    @Column(name = "is_closed", nullable = false)
    private boolean closed;

    @Column(name = "opens_at")
    private LocalTime opensAt;

    @Column(name = "closes_at")
    private LocalTime closesAt;

    @Column(name = "reason", length = 300)
    private String reason;

    public DealershipDateOverrideJpaEntity() {}

    public UUID getDealershipId() { return dealershipId; }
    public void setDealershipId(UUID dealershipId) { this.dealershipId = dealershipId; }

    public LocalDate getBusinessDate() { return businessDate; }
    public void setBusinessDate(LocalDate businessDate) { this.businessDate = businessDate; }

    public boolean isClosed() { return closed; }
    public void setClosed(boolean closed) { this.closed = closed; }

    public LocalTime getOpensAt() { return opensAt; }
    public void setOpensAt(LocalTime opensAt) { this.opensAt = opensAt; }

    public LocalTime getClosesAt() { return closesAt; }
    public void setClosesAt(LocalTime closesAt) { this.closesAt = closesAt; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

}
