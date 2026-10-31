package com.keyloop.scheduler.application.common.pagination;

import java.util.List;

public record PagedList<T>(
        List<T> items,
        int pageNumber,
        int totalPages,
        long totalCount,
        int pageSize
) {
    public boolean hasPreviousPage() {
        return pageNumber > 1;
    }

    public boolean hasNextPage() {
        return pageNumber < totalPages;
    }

    public static <T> PagedList<T> create(
            List<T> items,
            long totalCount,
            int pageNumber,
            int pageSize
    ) {
        int totalPages = (int) Math.ceil(
                (double) totalCount / pageSize
        );

        return new PagedList<>(
                items,
                pageNumber,
                totalPages,
                totalCount,
                pageSize
        );
    }
}