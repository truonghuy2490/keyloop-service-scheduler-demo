package com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.keyloop.scheduler.application.common.result.Error;

@Component("appointmentDetailsValidator")
public class Validators {

    public List<Error> validate(Query query) {

        if (query == null || !isValidGuid(query.appointmentId())) {
            return List.of(Errors.INVALID_APPOINTMENT_ID);
        }

        return List.of();
    }

    private boolean isValidGuid(String value) {

        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            UUID uuid = UUID.fromString(value);

            // Require canonical UUID format: 8-4-4-4-12
            return uuid.toString().equalsIgnoreCase(value);

        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}