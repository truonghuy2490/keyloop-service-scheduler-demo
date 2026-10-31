package com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;
import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.ErrorDetails;
import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.domain.entity.Appointment;

@Service("viewAppointmentDetailsHandler")
public class Handler {

    private static final Logger logger =
            LoggerFactory.getLogger(Handler.class);

    private final BaseRepository<Appointment, UUID> appointmentRepository;
    private final Validators validators;

    public Handler(
            BaseRepository<Appointment, UUID> appointmentRepository,
            Validators validators
    ) {
        this.appointmentRepository = appointmentRepository;
        this.validators = validators;
    }

    public Result<Response> handle(Query request) {

        // 1. Validate request
        List<Error> validationErrors = validators.validate(request);

        if (!validationErrors.isEmpty()) {
            return Result.failure(
                    new ErrorDetails(validationErrors)
            );
        }

        // 2. Convert String to UUID after validation
        UUID appointmentId = UUID.fromString(request.appointmentId());

        logger.debug(
                "Retrieving appointment with ID: {}",
                appointmentId
        );

        // 3. Retrieve appointment
        Optional<Appointment> appointment;

        try {
            appointment = appointmentRepository.findById(appointmentId);

        } catch (DataAccessException ex) {

            logger.error(
                    "Failed to retrieve appointment with ID: {}",
                    appointmentId,
                    ex
            );

            return Result.failure(
                    new ErrorDetails(
                            List.of(Errors.APPOINTMENT_RETRIEVAL_FAILED)
                    )
            );
        }

        // 4. Check whether appointment exists
        if (appointment.isEmpty()) {

            logger.warn(
                    "Appointment not found with ID: {}",
                    appointmentId
            );

            return Result.failure(
                    new ErrorDetails(
                            List.of(Errors.APPOINTMENT_NOT_FOUND)
                    )
            );
        }

        // 5. Map Domain to Response
        Response response = Response.from(appointment.get());

        logger.info(
                "Successfully retrieved appointment with ID: {}",
                appointmentId
        );

        return Result.success(response);
    }
}