package com.keyloop.scheduler.application.usecase.dealership.viewdealershipdetails;

import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.ErrorType;

public final class Errors {

    private Errors() {
    }

    public static final Error INVALID_DEALERSHIP_ID = new Error(
            "INVALID_DEALERSHIP_ID",
            "Dealership ID must be a valid UUID.",
            ErrorType.VALIDATION,
            "dealershipId"
    );

    public static final Error DEALERSHIP_NOT_FOUND = new Error(
            "DEALERSHIP_NOT_FOUND",
            "Dealership was not found.",
            ErrorType.NOT_FOUND,
            "dealershipId"
    );

    public static final Error DEALERSHIP_RETRIEVAL_FAILED = new Error(
            "DEALERSHIP_RETRIEVAL_FAILED",
            "Unable to retrieve dealership.",
            ErrorType.FAILURE,
            null
    );
}