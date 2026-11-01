package com.keyloop.scheduler.persistence.entity;

import com.keyloop.scheduler.persistence.entity.common.SoftDeletedCreatedJpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "dealership_business_hours")
public class DealershipBusinessHoursJpaEntity
        extends SoftDeletedCreatedJpaEntity {

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

    protected DealershipBusinessHoursJpaEntity() {
    }

    private DealershipBusinessHoursJpaEntity(Builder builder) {
        super(builder);

        this.dealershipId = builder.dealershipId;
        this.dayOfWeek = builder.dayOfWeek;
        this.closed = builder.closed;
        this.opensAt = builder.opensAt;
        this.closesAt = builder.closesAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
            extends SoftDeletedCreatedJpaEntity.Builder<Builder> {

        private UUID dealershipId;
        private Short dayOfWeek;
        private boolean closed;
        private LocalTime opensAt;
        private LocalTime closesAt;

        public Builder dealershipId(UUID dealershipId) {
            this.dealershipId = dealershipId;
            return this;
        }

        public Builder dayOfWeek(Short dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
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

        @Override
        protected Builder self() {
            return this;
        }

        public DealershipBusinessHoursJpaEntity build() {
            return new DealershipBusinessHoursJpaEntity(this);
        }
    }

    public UUID getDealershipId() {
        return dealershipId;
    }

    public Short getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isClosed() {
        return closed;
    }

    public LocalTime getOpensAt() {
        return opensAt;
    }

    public LocalTime getClosesAt() {
        return closesAt;
    }
}