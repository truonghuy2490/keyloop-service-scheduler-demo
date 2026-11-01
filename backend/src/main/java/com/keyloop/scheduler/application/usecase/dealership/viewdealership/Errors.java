package com.keyloop.scheduler.application.usecase.dealership.viewdealership;

import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.ErrorType;

public final class Errors {

    private Errors() {}

    public static final Error DEALERSHIP_RETRIEVAL_FAILED =
            new Error(
                    "DEALERSHIP_RETRIEVAL_FAILED",
                    "Unable to retrieve dealerships.",
                    ErrorType.FAILURE,
                    null
            );
}
