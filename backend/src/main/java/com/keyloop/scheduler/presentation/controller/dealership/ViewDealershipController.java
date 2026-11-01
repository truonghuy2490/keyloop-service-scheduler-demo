package com.keyloop.scheduler.presentation.controller.dealership;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.application.usecase.dealership.viewdealership.Handler;
import com.keyloop.scheduler.application.usecase.dealership.viewdealership.Query;
import com.keyloop.scheduler.application.usecase.dealership.viewdealership.Response;
import com.keyloop.scheduler.application.common.pagination.PagedList;
import com.keyloop.scheduler.presentation.utility.ErrorOrExtensions;

@RestController
@RequestMapping("/api/{version}/dealerships")
@Tag(
    name = "Dealerships",
    description = "Dealership management APIs"
)
public class ViewDealershipController {

    private final Handler handler;

    public ViewDealershipController(
        @Qualifier("viewDealershipHandler") Handler handler
    ) {
        this.handler = handler;
    }

    @GetMapping(version = "1.0")
    @Operation(
        summary = "View dealerships",
        description = "Retrieve dealerships with pagination."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved dealerships"
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
    public CompletableFuture<ResponseEntity<Object>> getDealerships(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        Query query = new Query(pageNumber, pageSize);

        return handler.handle(query)
                .thenApply(ErrorOrExtensions::toResponse);
    }
}