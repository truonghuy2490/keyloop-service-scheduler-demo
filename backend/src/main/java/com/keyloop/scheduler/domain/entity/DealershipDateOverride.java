package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

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

    private DealershipDateOverride(Builder builder) {
        super(builder);
        this.dealershipId = builder.dealershipId;
        this.businessDate = builder.businessDate;
        this.isClosed = builder.isClosed;
        this.opensAt = builder.opensAt;
        this.closesAt = builder.closesAt;
        this.reason = builder.reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
            extends SoftDeletableAuditableEntity.Builder<Builder> {

        private UUID dealershipId;
        private LocalDate businessDate;
        private boolean isClosed;
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

        public Builder isClosed(boolean isClosed) {
            this.isClosed = isClosed;
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

        public DealershipDateOverride build() {
            return new DealershipDateOverride(this);
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
