package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import java.time.Instant;
import jakarta.persistence.Version;

@Entity
@Table(name = "appointment")
public class AppointmentJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Column(name = "dealership_id", nullable = false)
    private UUID dealershipId;

    @Column(name = "service_type_id", nullable = false)
    private UUID serviceTypeId;

    @Column(name = "technician_id", nullable = false)
    private UUID technicianId;

    @Column(name = "service_bay_id", nullable = false)
    private UUID serviceBayId;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "cancellation_reason", length = 500)
    private String cancellationReason;

    public AppointmentJpaEntity() {}

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public UUID getVehicleId() { return vehicleId; }
    public void setVehicleId(UUID vehicleId) { this.vehicleId = vehicleId; }

    public UUID getDealershipId() { return dealershipId; }
    public void setDealershipId(UUID dealershipId) { this.dealershipId = dealershipId; }

    public UUID getServiceTypeId() { return serviceTypeId; }
    public void setServiceTypeId(UUID serviceTypeId) { this.serviceTypeId = serviceTypeId; }

    public UUID getTechnicianId() { return technicianId; }
    public void setTechnicianId(UUID technicianId) { this.technicianId = technicianId; }

    public UUID getServiceBayId() { return serviceBayId; }
    public void setServiceBayId(UUID serviceBayId) { this.serviceBayId = serviceBayId; }

    public Instant getStartTime() { return startTime; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }

    public Instant getEndTime() { return endTime; }
    public void setEndTime(Instant endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

}
