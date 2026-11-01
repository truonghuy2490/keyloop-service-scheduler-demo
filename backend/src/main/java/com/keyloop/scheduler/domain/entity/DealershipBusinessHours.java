package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableCreatedEntity;

import java.time.LocalTime;
import java.util.UUID;

public class DealershipBusinessHours extends SoftDeletableCreatedEntity {

    private UUID dealershipId;
    private Short dayOfWeek;
    private boolean closed;
    private LocalTime opensAt;
    private LocalTime closesAt;

    protected DealershipBusinessHours() {
    }

    private DealershipBusinessHours(Builder builder) {
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
            extends SoftDeletableCreatedEntity.Builder<Builder> {

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

        public DealershipBusinessHours build() {
            return new DealershipBusinessHours(this);
        }
    }

    public UUID getDealershipId() {
        return dealershipId;
    }

    public Short getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean getIsClosed() {
        return closed;
    }

    public LocalTime getOpensAt() {
        return opensAt;
    }

    public LocalTime getClosesAt() {
        return closesAt;
    }
}