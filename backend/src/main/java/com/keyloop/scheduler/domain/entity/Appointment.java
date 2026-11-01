package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

import java.time.Instant;
import java.util.UUID;

public class Appointment extends SoftDeletableAuditableEntity {

    private UUID customerId;
    private UUID vehicleId;
    private UUID dealershipId;
    private UUID serviceTypeId;
    private UUID technicianId;
    private UUID serviceBayId;

    private Instant startTime;
    private Instant endTime;

    private String status;
    private Long version;
    private String cancellationReason;

    protected Appointment() {
    }

    private Appointment(Builder builder) {
        super(builder);

        this.customerId = builder.customerId;
        this.vehicleId = builder.vehicleId;
        this.dealershipId = builder.dealershipId;
        this.serviceTypeId = builder.serviceTypeId;
        this.technicianId = builder.technicianId;
        this.serviceBayId = builder.serviceBayId;

        this.startTime = builder.startTime;
        this.endTime = builder.endTime;

        this.status = builder.status;
        this.version = builder.version;
        this.cancellationReason = builder.cancellationReason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
            extends SoftDeletableAuditableEntity.Builder<Builder> {

        private UUID customerId;
        private UUID vehicleId;
        private UUID dealershipId;
        private UUID serviceTypeId;
        private UUID technicianId;
        private UUID serviceBayId;

        private Instant startTime;
        private Instant endTime;

        private String status;
        private Long version;
        private String cancellationReason;

        public Builder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder vehicleId(UUID vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder dealershipId(UUID dealershipId) {
            this.dealershipId = dealershipId;
            return this;
        }

        public Builder serviceTypeId(UUID serviceTypeId) {
            this.serviceTypeId = serviceTypeId;
            return this;
        }

        public Builder technicianId(UUID technicianId) {
            this.technicianId = technicianId;
            return this;
        }

        public Builder serviceBayId(UUID serviceBayId) {
            this.serviceBayId = serviceBayId;
            return this;
        }

        public Builder startTime(Instant startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Instant endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder version(Long version) {
            this.version = version;
            return this;
        }

        public Builder cancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Appointment build() {
            return new Appointment(this);
        }
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public UUID getDealershipId() {
        return dealershipId;
    }

    public UUID getServiceTypeId() {
        return serviceTypeId;
    }

    public UUID getTechnicianId() {
        return technicianId;
    }

    public UUID getServiceBayId() {
        return serviceBayId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public Long getVersion() {
        return version;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }
}
