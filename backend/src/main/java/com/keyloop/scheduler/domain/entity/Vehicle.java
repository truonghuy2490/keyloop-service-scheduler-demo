package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.SoftDeletableAuditableEntity;

import java.time.Instant;
import java.util.UUID;

public class Vehicle extends SoftDeletableAuditableEntity {
    private UUID customerId;
    private String registrationNumber;
    private String make;
    private String model;
    private Short modelYear;
    public Vehicle() {
    }
    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Short getModelYear() {
        return modelYear;
    }

    public void setModelYear(Short modelYear) {
        this.modelYear = modelYear;
    }
}
