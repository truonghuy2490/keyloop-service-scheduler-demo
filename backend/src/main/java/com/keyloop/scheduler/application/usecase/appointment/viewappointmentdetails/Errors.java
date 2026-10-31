package com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails;

import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.ErrorType;

public final class Errors {

    private Errors() {}

    public static final Error INVALID_APPOINTMENT_ID =
            new Error(
                    "INVALID_APPOINTMENT_ID",
                    "Appointment ID must be a valid UUID.",
                    ErrorType.VALIDATION,
                    "appointmentId"
            );

    public static final Error APPOINTMENT_NOT_FOUND =
            new Error(
                    "APPOINTMENT_NOT_FOUND",
                    "Appointment was not found.",
                    ErrorType.NOT_FOUND,
                    "appointmentId"
            );

    public static final Error APPOINTMENT_RETRIEVAL_FAILED =
            new Error(
                    "APPOINTMENT_RETRIEVAL_FAILED",
                    "Unable to retrieve appointment.",
                    ErrorType.FAILURE,
                    null
            );
}