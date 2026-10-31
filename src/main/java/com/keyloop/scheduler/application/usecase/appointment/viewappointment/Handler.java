package com.keyloop.scheduler.application.usecase.appointment.viewappointment;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;
import com.keyloop.scheduler.application.common.result.ErrorDetails;
import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.domain.entity.Appointment;

@Service
public class Handler {

    private static final Logger logger =
            LoggerFactory.getLogger(Handler.class);

    private final BaseRepository<Appointment, UUID> appointmentRepository;

    public Handler(BaseRepository<Appointment, UUID> appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Result<List<Response>> handle(Query query) {

        logger.debug("Retrieving all appointments.");

        List<Appointment> appointments = appointmentRepository.findAll();

        if (appointments == null) {
            logger.error("Appointment repository returned null.");

            return Result.failure(
                    new ErrorDetails(
                            List.of(Errors.APPOINTMENT_RETRIEVAL_FAILED)
                    )
            );
        }

        List<Response> response = appointments.stream()
                .map(Response::from)
                .toList();

        logger.info(
                "Successfully retrieved {} appointments.",
                response.size()
        );

        return Result.success(response);
    }
}