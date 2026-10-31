package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "technician_qualification")
public class TechnicianQualificationJpaEntity {
    @EmbeddedId
    private TechnicianQualificationId id;
    @Column(name = "qualified_at", nullable = false, insertable = false, updatable = false)
    private Instant qualifiedAt;
    public TechnicianQualificationJpaEntity() {}
    public TechnicianQualificationId getId() { return id; }
    public void setId(TechnicianQualificationId id) { this.id = id; }
    public Instant getQualifiedAt() { return qualifiedAt; }
    public void setQualifiedAt(Instant qualifiedAt) { this.qualifiedAt = qualifiedAt; }
}
