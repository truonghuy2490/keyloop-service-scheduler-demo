package com.keyloop.scheduler.presentation.controller.appointment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails.Handler;
import com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails.Query;
import com.keyloop.scheduler.application.usecase.appointment.viewappointmentdetails.Response;
import com.keyloop.scheduler.presentation.utility.ErrorOrExtensions;

@RestController
@RequestMapping("/api/{version}/appointments")
@Tag(
        name = "Appointments",
        description = "Appointment management APIs"
)
public class ViewAppointmentByIdController {

    private final Handler handler;

    public ViewAppointmentByIdController(
        @Qualifier("viewAppointmentDetailsHandler") Handler handler) {
        this.handler = handler;
    }

    @GetMapping(value = "/{appointmentId}", version = "1.0")
    @Operation(
            summary = "Get appointment by ID",
            description = "Retrieve an appointment using its unique ID."
    )
    @ApiResponse(responseCode = "200", description = "Appointment retrieved successfully")
    @ApiResponse(
            responseCode = "400",
            description = "Invalid appointment ID",
            content = @Content(
                    schema = @Schema(implementation = ProblemDetail.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Appointment not found",
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
    public ResponseEntity<Object> getAppointmentById(
        @PathVariable String appointmentId
    ) {

        Query query = new Query(appointmentId);

        Result<Response> result = handler.handle(query);

        return ErrorOrExtensions.toResponse(result);
    }
}