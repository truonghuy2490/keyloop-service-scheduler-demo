package com.keyloop.scheduler.presentation.utility;

import com.keyloop.scheduler.application.common.result.Error;
import com.keyloop.scheduler.application.common.result.ErrorDetails;
import com.keyloop.scheduler.application.common.result.ErrorType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.List;

public final class ProblemDetailMapper {

    private ProblemDetailMapper() {}

    public static ProblemDetail fromErrorDetails(
            ErrorDetails errorDetails
    ) {
        if (errorDetails == null ||
                errorDetails.errors() == null ||
                errorDetails.errors().isEmpty()) {
            return unexpectedError();
        }

        List<Error> errors = errorDetails.errors();

        ErrorType errorType = errors.get(0).type();

        // All errors in one Result must have the same ErrorType.
        if (errors.stream().anyMatch(
                error -> error.type() != errorType
        )) {
            throw new IllegalArgumentException(
                    "All errors must have the same ErrorType."
            );
        }

        ProblemDetail problem = ProblemDetail.forStatus(
                mapHttpStatus(errorType)
        );

        problem.setType(mapProblemType(errorType));
        problem.setTitle(mapTitle(errorType));
        problem.setDetail(errors.get(0).description());

        problem.setProperty(
                "errors",
                errors.stream()
                        .map(error -> new ErrorItem(
                                error.code(),
                                error.description(),
                                error.property()
                        ))
                        .toList()
        );

        return problem;
    }

    public static ProblemDetail unexpectedError() {
        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        problem.setType(URI.create("about:blank"));
        problem.setTitle("Internal Server Error");
        problem.setDetail(
                "An unexpected error occurred on the server."
        );

        return problem;
    }

    private static HttpStatus mapHttpStatus(ErrorType type) {
        return switch (type) {
            case VALIDATION -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case FAILURE -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private static URI mapProblemType(ErrorType type) {
        return switch (type) {
            case VALIDATION,
                 UNAUTHORIZED,
                 FORBIDDEN,
                 NOT_FOUND,
                 CONFLICT,
                 FAILURE -> URI.create("about:blank");
        };
    }

    private static String mapTitle(ErrorType type) {
        return switch (type) {
            case VALIDATION -> "Bad Request";
            case UNAUTHORIZED -> "Unauthorized";
            case FORBIDDEN -> "Forbidden";
            case NOT_FOUND -> "Not Found";
            case CONFLICT -> "Conflict";
            case FAILURE -> "Internal Server Error";
        };
    }

    private record ErrorItem(
            String code,
            String description,
            String property
    ) {}
}
