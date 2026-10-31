package com.keyloop.scheduler.application.usecase.appointment.viewappointment;

import java.util.UUID;

import com.keyloop.scheduler.domain.entity.Appointment;

public record Response(
        UUID id,
        UUID customerId,
        UUID vehicleId,
        String status
) {
    public static Response from(Appointment appointment) {
        return new Response(
                appointment.getId(),
                appointment.getCustomerId(),
                appointment.getVehicleId(),
                appointment.getStatus()
        );
    }
}