package com.keyloop.scheduler.application.usecase.dealership.viewdealership;

import java.util.UUID;

import com.keyloop.scheduler.domain.entity.Dealership;

public record Response(
        UUID id,
        String name,
        String timeZone
) {
    public static Response from(Dealership dealership) {
        return new Response(
                dealership.getId(),
                dealership.getName(),
                dealership.getTimeZone()
        );
    }
}