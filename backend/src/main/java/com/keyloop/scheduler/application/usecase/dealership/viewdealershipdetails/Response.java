package com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.keyloop.scheduler.application.abstraction.dataaccess.DealershipRepository.DealershipDetailsData;

public record Response(
        UUID id,
        String name,
        String timeZone,
        List<WorkingHours> workingHours,
        List<DateOverride> dateOverrides,
        ResourceSummary resourceSummary
) {

    public record WorkingHours(
            DayOfWeek dayOfWeek,
            boolean isClosed,
            LocalTime opensAt,
            LocalTime closesAt
    ) {}

    public record DateOverride(
            LocalDate businessDate,
            boolean isClosed,
            LocalTime opensAt,
            LocalTime closesAt,
            String reason
    ) {}

    public record ResourceSummary(
            long serviceBayCount,
            long technicianCount,
            long serviceCount
    ) {}

    public static Response from(DealershipDetailsData source) {

        List<WorkingHours> workingHours = source.workingHours()
                .stream()
                .map(item -> new WorkingHours(
                        item.dayOfWeek(),
                        item.isClosed(),
                        item.opensAt(),
                        item.closesAt()
                ))
                .toList();

        List<DateOverride> dateOverrides = source.dateOverrides()
                .stream()
                .map(item -> new DateOverride(
                        item.businessDate(),
                        item.isClosed(),
                        item.opensAt(),
                        item.closesAt(),
                        item.reason()
                ))
                .toList();

        ResourceSummary resourceSummary = new ResourceSummary(
                source.resourceSummary().serviceBayCount(),
                source.resourceSummary().technicianCount(),
                source.resourceSummary().serviceCount()
        );

        return new Response(
                source.id(),
                source.name(),
                source.timeZone(),
                workingHours,
                dateOverrides,
                resourceSummary
        );
    }
}