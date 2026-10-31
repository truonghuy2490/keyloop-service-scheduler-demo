package com.keyloop.scheduler.domain.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class TechnicianQualificationId implements Serializable {

    private UUID technicianId;
    private UUID serviceTypeId;

    public TechnicianQualificationId() {
    }

    public TechnicianQualificationId(UUID technicianId, UUID serviceTypeId) {
        this.technicianId = technicianId;
        this.serviceTypeId = serviceTypeId;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof TechnicianQualificationId that)) return false;
        return Objects.equals(technicianId, that.technicianId)
                && Objects.equals(serviceTypeId, that.serviceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technicianId, serviceTypeId);
    }
}
