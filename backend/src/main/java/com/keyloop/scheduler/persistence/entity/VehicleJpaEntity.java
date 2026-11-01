package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

import com.keyloop.scheduler.persistence.entity.common.SoftDeletedAuditableJpaEntity;

@Entity
@Table(name = "vehicle")
public class VehicleJpaEntity extends SoftDeletedAuditableJpaEntity {
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "registration_number", nullable = false, length = 32)
    private String registrationNumber;

    @Column(name = "make", nullable = false, length = 100)
    private String make;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "model_year")
    private Short modelYear;

    public VehicleJpaEntity() {}

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Short getModelYear() { return modelYear; }
    public void setModelYear(Short modelYear) { this.modelYear = modelYear; }

}
