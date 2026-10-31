package com.keyloop.scheduler.application.usecase.appointment.viewappointment;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.keyloop.scheduler.application.abstraction.dataaccess.BaseRepository;
import com.keyloop.scheduler.application.common.pagination.PagedList;
import com.keyloop.scheduler.application.common.result.ErrorDetails;
import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.domain.entity.Appointment;

@Service
public class Handler {

    private static final Logger logger =
            LoggerFactory.getLogger(Handler.class);

    private final BaseRepository<Appointment, UUID> appointmentRepository;

    public Handler(
            BaseRepository<Appointment, UUID> appointmentRepository
    ) {
        this.appointmentRepository = appointmentRepository;
    }

    public Result<PagedList<Response>> handle(Query request) {

        logger.debug("Retrieving appointments.");
        
        int pageNumber = request.pageNumber();
        int pageSize = request.pageSize();

        // 1. Retrieve paginated appointments
        PagedList<Appointment> appointments;

        try {
                appointments = appointmentRepository.findAll(
                        pageNumber,
                        pageSize
                );
        } catch (DataAccessException ex) {
                logger.error("Failed to retrieve appointments from database.", ex);

                return Result.failure(
                        new ErrorDetails(
                                List.of(Errors.APPOINTMENT_RETRIEVAL_FAILED)
                        )
                );
        }

        if (appointments.items().isEmpty()) {
                logger.info("No appointments found.");

                return Result.success(
                        PagedList.create(
                                List.of(),
                                0,
                                pageNumber,
                                pageSize
                        )
                );
        }

        // 2. Map Domain to Response
        List<Response> items = appointments.items()
                .stream()
                .map(Response::from)
                .toList();

        // 3. Return paginated response
        PagedList<Response> response = PagedList.create(
                items,
                appointments.totalCount(),
                pageNumber,
                pageSize
        );

        logger.info(
                "Successfully retrieved {} appointments.",
                items.size()
        );

        return Result.success(response);
    }
}