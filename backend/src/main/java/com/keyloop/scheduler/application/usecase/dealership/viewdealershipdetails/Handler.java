package com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.keyloop.scheduler.application.abstraction.dataaccess.DealershipRepository;
import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.ErrorDetails;
import com.keyloop.scheduler.application.common.result.Result;
import com.keyloop.scheduler.application.abstraction.dataaccess.DealershipRepository.DealershipDetailsData;

@Service("viewDealershipDetailsHandler")
public class Handler {

    private static final Logger logger =
            LoggerFactory.getLogger(Handler.class);

    private final DealershipRepository dealershipRepository;
    private final Validators validators;

    public Handler(
            DealershipRepository dealershipRepository,
            @Qualifier("dealershipDetailsValidator") Validators validators
    ) {
        this.dealershipRepository = dealershipRepository;
        this.validators = validators;
    }

    @Async("applicationTaskExecutor")
    public CompletableFuture<Result<Response>> handle(Query request) {

        // 1. Validate request
        List<Error> validationErrors = validators.validate(request);

        if (!validationErrors.isEmpty()) {
            return CompletableFuture.completedFuture(
                    Result.failure(
                            new ErrorDetails(validationErrors)
                    )
            );
        }

        // 2. Convert String to UUID after validation
        UUID dealershipId = UUID.fromString(request.dealershipId());

        logger.debug(
                "Retrieving dealership details with ID: {}",
                dealershipId
        );

        // 3. Retrieve dealership details
        Optional<DealershipDetailsData> dealershipDetails;

        try {
            dealershipDetails =
                    dealershipRepository.findDetailsById(dealershipId);

        } catch (DataAccessException ex) {

            logger.error(
                    "Failed to retrieve dealership details with ID: {}",
                    dealershipId,
                    ex
            );

            return CompletableFuture.completedFuture(
                    Result.failure(
                            new ErrorDetails(
                                    List.of(Errors.DEALERSHIP_RETRIEVAL_FAILED)
                            )
                    )
            );
        }

        // 4. Check whether dealership exists
        if (dealershipDetails.isEmpty()) {

            logger.warn(
                    "Dealership not found with ID: {}",
                    dealershipId
            );

            return CompletableFuture.completedFuture(
                    Result.failure(
                            new ErrorDetails(
                                    List.of(Errors.DEALERSHIP_NOT_FOUND)
                            )
                    )
            );
        }

        // 5. Map read model to Response
        Response response = Response.from(dealershipDetails.get());

        logger.info(
                "Successfully retrieved dealership details with ID: {}",
                dealershipId
        );

        return CompletableFuture.completedFuture(
                Result.success(response)
        );
    }
}