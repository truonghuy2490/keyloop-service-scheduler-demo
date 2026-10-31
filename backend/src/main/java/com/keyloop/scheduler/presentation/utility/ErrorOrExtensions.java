
package com.keyloop.scheduler.presentation.utility;

import com.keyloop.scheduler.application.common.result.Result;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public final class ErrorOrExtensions {

    private ErrorOrExtensions() {}

    public static <T> ResponseEntity<Object> toResponse(
            Result<T> result
    ) {
        if (result instanceof Result.Success<T> success) {
            return ResponseEntity.ok(success.value());
        }

        if (result instanceof Result.Failure<T> failure) {
            ProblemDetail problem =
                    ProblemDetailMapper.fromErrorDetails(
                            failure.errorDetails()
                    );

            return ResponseEntity
                    .status(problem.getStatus())
                    .body(problem);
        }

        throw new IllegalStateException(
                "Unsupported Result type: " + result.getClass().getName()
        );
    }
}
