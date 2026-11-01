package com.keyloop.scheduler.persistence.entity;

import com.keyloop.scheduler.persistence.entity.common.SoftDeletedAuditableJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "dealership_date_override")
public class DealershipDateOverrideJpaEntity
        extends SoftDeletedAuditableJpaEntity {

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

    protected DealershipDateOverrideJpaEntity() {
    }

    private DealershipDateOverrideJpaEntity(Builder builder) {
        super(builder);
        this.dealershipId = builder.dealershipId;
        this.businessDate = builder.businessDate;
        this.closed = builder.closed;
        this.opensAt = builder.opensAt;
        this.closesAt = builder.closesAt;
        this.reason = builder.reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
            extends SoftDeletedAuditableJpaEntity.Builder<Builder> {

        private UUID dealershipId;
        private LocalDate businessDate;
        private boolean closed;
        private LocalTime opensAt;
        private LocalTime closesAt;
        private String reason;

        public Builder dealershipId(UUID dealershipId) {
            this.dealershipId = dealershipId;
            return this;
        }

        public Builder businessDate(LocalDate businessDate) {
            this.businessDate = businessDate;
            return this;
        }

        public Builder closed(boolean closed) {
            this.closed = closed;
            return this;
        }

        public Builder opensAt(LocalTime opensAt) {
            this.opensAt = opensAt;
            return this;
        }

        public Builder closesAt(LocalTime closesAt) {
            this.closesAt = closesAt;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public DealershipDateOverrideJpaEntity build() {
            return new DealershipDateOverrideJpaEntity(this);
        }
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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
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
