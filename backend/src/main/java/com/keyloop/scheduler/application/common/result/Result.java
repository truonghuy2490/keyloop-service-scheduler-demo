package com.keyloop.scheduler.application.common.result;

public sealed interface Result<T> {

    record Success<T>(T value) implements Result<T> {}

    record Failure<T>(
            ErrorDetails errorDetails
    ) implements Result<T> {}

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Result<T> failure(ErrorDetails errors) {
        return new Failure<>(errors);
    }

    default boolean isSuccess() {
        return this instanceof Success<?>;
    }

    default boolean isFailure() {
        return this instanceof Failure<?>;
    }
}