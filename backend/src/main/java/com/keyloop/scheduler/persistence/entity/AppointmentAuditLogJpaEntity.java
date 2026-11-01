package com.keyloop.scheduler.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.keyloop.scheduler.persistence.entity.common.CreatedAtJpaEntity;

@Entity
@Table(name = "appointment_audit_log")
public class AppointmentAuditLogJpaEntity extends CreatedAtJpaEntity {
    @Column(name = "appointment_id", nullable = false)
    private UUID appointmentId;

    @Column(name = "action", nullable = false, length = 80)
    private String action;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private String detailsJson;

    @Column(name = "actor_id")
    private UUID actorId;

    public AppointmentAuditLogJpaEntity() {}

    public UUID getAppointmentId() { return appointmentId; }
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDetailsJson() { return detailsJson; }
    public void setDetailsJson(String detailsJson) { this.detailsJson = detailsJson; }

    public UUID getActorId() { return actorId; }
    public void setActorId(UUID actorId) { this.actorId = actorId; }

}
