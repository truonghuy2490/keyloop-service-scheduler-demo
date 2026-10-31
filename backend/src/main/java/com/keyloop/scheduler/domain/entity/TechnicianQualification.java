package com.keyloop.scheduler.domain.entity;

import java.time.Instant;
import java.util.UUID;

public class TechnicianQualification {

    private UUID technicianId;
    private UUID serviceTypeId;
    private Instant qualifiedAt;

    public TechnicianQualification() {
    }

    public UUID getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(UUID technicianId) {
        this.technicianId = technicianId;
    }

    public UUID getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(UUID serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Instant getQualifiedAt() {
        return qualifiedAt;
    }

    public void setQualifiedAt(Instant qualifiedAt) {
        this.qualifiedAt = qualifiedAt;
    }

}
