package com.keyloop.scheduler.application.usecase.dealership.viewdealership;

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
import com.keyloop.scheduler.domain.entity.Dealership;

@Service("viewDealershipHandler")
public class Handler {

    private static final Logger logger =
            LoggerFactory.getLogger(Handler.class);

    private final BaseRepository<Dealership, UUID> dealershipRepository;

    public Handler(
            BaseRepository<Dealership, UUID> dealershipRepository
    ) {
        this.dealershipRepository = dealershipRepository;
    }

    public Result<PagedList<Response>> handle(Query request) {

        logger.debug("Retrieving dealerships.");

        int pageNumber = request.pageNumber();
        int pageSize = request.pageSize();

        // 1. Retrieve paginated dealerships
        PagedList<Dealership> dealerships;

        try {
            dealerships = dealershipRepository.findAll(
                    pageNumber,
                    pageSize
            );
        } catch (DataAccessException ex) {
            logger.error(
                    "Failed to retrieve dealerships from database.",
                    ex
            );

            return Result.failure(
                    new ErrorDetails(
                            List.of(Errors.DEALERSHIP_RETRIEVAL_FAILED)
                    )
            );
        }

        if (dealerships.items().isEmpty()) {
            logger.info("No dealerships found.");

            return Result.success(
                    PagedList.create(
                            List.of(),
                            dealerships.totalCount(),
                            pageNumber,
                            pageSize
                    )
            );
        }

        // 2. Map Domain to Response
        List<Response> items = dealerships.items()
                .stream()
                .map(Response::from)
                .toList();

        // 3. Return paginated response
        PagedList<Response> response = PagedList.create(
                items,
                dealerships.totalCount(),
                pageNumber,
                pageSize
        );

        logger.info(
                "Successfully retrieved {} dealerships.",
                items.size()
        );

        return Result.success(response);
    }
}