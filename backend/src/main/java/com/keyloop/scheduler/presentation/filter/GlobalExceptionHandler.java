package com.keyloop.scheduler.presentation.filter;

import com.keyloop.scheduler.presentation.utility.ProblemDetailMapper;

import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. @Valid request DTO validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getCode()
                ))
                .toList();

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );

        problem.setTitle("Validation Error");
        problem.setDetail("One or more validation errors occurred.");

        problem.setProperty("errors", errors);

        return handleExceptionInternal(
                ex,
                problem,
                headers,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    // 2. Constraint violations, e.g. method parameter validation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(
            ConstraintViolationException ex
    ) {
        List<ValidationError> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ValidationError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage(),
                        "CONSTRAINT_VIOLATION"
                ))
                .toList();

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );

        problem.setTitle("Validation Error");
        problem.setDetail("One or more constraint violations occurred.");
        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    // 3. Invalid JSON or request body cannot be parsed
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );

        problem.setTitle("Invalid Request Body");
        problem.setDetail(
                "The request body is missing or has an invalid format."
        );

        return handleExceptionInternal(
                ex,
                problem,
                headers,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    // 4. Unsupported HTTP method
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.METHOD_NOT_ALLOWED
        );

        problem.setTitle("Method Not Allowed");
        problem.setDetail("The HTTP method is not supported for this endpoint.");

        return handleExceptionInternal(
                ex,
                problem,
                headers,
                HttpStatus.METHOD_NOT_ALLOWED,
                request
        );
    }

    // 5. Unsupported Content-Type
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );

        problem.setTitle("Unsupported Media Type");
        problem.setDetail(
                "The request Content-Type is not supported."
        );

        return handleExceptionInternal(
                ex,
                problem,
                headers,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                request
        );
    }

    // 6. Unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpectedException(
            Exception ex
    ) {
        logger.error("Unexpected exception occurred", ex);

        ProblemDetail problem =
                ProblemDetailMapper.unexpectedError();

        return ResponseEntity
                .status(problem.getStatus())
                .body(problem);
    }

    private record ValidationError(
            String property,
            String description,
            String code
    ) {}

    // 7. HandlerMethodValidationException
    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(status);
    
        problem.setTitle("Validation Error");
        problem.setDetail("One or more request validation errors occurred.");

        return handleExceptionInternal(
                ex,
                problem,
                headers,
                status,
                request
        );
    }
}
