package com.keyloop.scheduler.application.usecase.dealership.viewdealership;

public record Query(
        int pageNumber,
        int pageSize
) {
    public Query {
        if (pageNumber < 1) {
            pageNumber = 1;
        }

        if (pageSize < 1) {
            pageSize = 10;
        }
    }
}