package com.keyloop.scheduler.presentation.controller.dealership;

import java.util.concurrent.CompletableFuture;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Handler;
import com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails.Query;
import com.keyloop.scheduler.presentation.utility.ErrorOrExtensions;

@RestController
@RequestMapping("/api/{version}/dealerships")
@Tag(
        name = "Dealerships",
        description = "Dealership management APIs"
)
public class ViewDealershipByIdController {

    private final Handler handler;

    public ViewDealershipByIdController(
            @Qualifier("viewDealershipDetailsHandler") Handler handler
    ) {
        this.handler = handler;
    }

    @GetMapping(value = "/{dealershipId}", version = "1.0")
    @Operation(
            summary = "Get dealership by ID",
            description = "Retrieve a dealership using its unique ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dealership retrieved successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid dealership ID",
            content = @Content(
                    schema = @Schema(implementation = ProblemDetail.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Dealership not found",
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
    public CompletableFuture<ResponseEntity<Object>> getDealershipById(
            @PathVariable String dealershipId
    ) {

        Query query = new Query(dealershipId);

        return handler.handle(query)
                .thenApply(ErrorOrExtensions::toResponse);
    }
}