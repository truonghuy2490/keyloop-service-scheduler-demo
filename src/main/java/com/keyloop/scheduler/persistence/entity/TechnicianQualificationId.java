package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class TechnicianQualificationId implements Serializable {
    @Column(name = "technician_id", nullable = false)
    private UUID technicianId;
    @Column(name = "service_type_id", nullable = false)
    private UUID serviceTypeId;
    public TechnicianQualificationId() {}
    public TechnicianQualificationId(UUID technicianId, UUID serviceTypeId) { this.technicianId = technicianId; this.serviceTypeId = serviceTypeId; }
    public UUID getTechnicianId() { return technicianId; }
    public void setTechnicianId(UUID technicianId) { this.technicianId = technicianId; }
    public UUID getServiceTypeId() { return serviceTypeId; }
    public void setServiceTypeId(UUID serviceTypeId) { this.serviceTypeId = serviceTypeId; }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof TechnicianQualificationId that)) return false; return Objects.equals(technicianId, that.technicianId) && Objects.equals(serviceTypeId, that.serviceTypeId); }
    @Override public int hashCode() { return Objects.hash(technicianId, serviceTypeId); }
}
