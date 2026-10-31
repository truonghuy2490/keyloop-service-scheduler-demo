package com.keyloop.scheduler.application.common.result;

public record Error(
        String code,
        String description,
        ErrorType type,
        String property
) {}