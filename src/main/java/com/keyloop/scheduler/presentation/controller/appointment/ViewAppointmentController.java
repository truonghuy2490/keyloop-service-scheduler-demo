package com.keyloop.scheduler.presentation.controller.appointment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.application.common.pagination.PagedList;
import com.keyloop.scheduler.application.usecase.appointment.viewappointment.Handler;
import com.keyloop.scheduler.application.usecase.appointment.viewappointment.Query;
import com.keyloop.scheduler.application.usecase.appointment.viewappointment.Response;
import com.keyloop.scheduler.presentation.utility.ErrorOrExtensions;

@RestController
@RequestMapping("/api/{version}/appointments")
@Tag(
    name = "Appointments",
    description = "Appointment management APIs"
)
public class ViewAppointmentController {

    private final Handler handler;

    public ViewAppointmentController(Handler handler) {
        this.handler = handler;
    }

    @GetMapping(version = "1.0")
    @Operation(
        summary = "View appointments",
        description = "Retrieve appointments with pagination."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved appointments"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Bad request",
        content = @Content(
            schema = @Schema(implementation = ProblemDetail.class)
        )
    )
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error",
        content = @Content(
            schema = @Schema(implementation = ProblemDetail.class)
        )
    )
    public ResponseEntity<Object> getAppointments(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        Query query = new Query(pageNumber, pageSize);

        Result<PagedList<Response>> result = handler.handle(query);

        return ErrorOrExtensions.toResponse(result);
    }
}