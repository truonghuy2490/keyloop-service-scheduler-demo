package com.keyloop.scheduler.domain.entity;

import com.keyloop.scheduler.domain.common.CreatedAtEntity;

import java.util.UUID;

public class AppointmentAuditLog extends CreatedAtEntity {
    private UUID appointmentId;
    private String action;
    private String detailsJson;
    private UUID actorId;
    public AppointmentAuditLog() {
    }
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetailsJson() {
        return detailsJson;
    }

    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    public UUID getActorId() {
        return actorId;
    }

    public void setActorId(UUID actorId) {
        this.actorId = actorId;
    }
}
