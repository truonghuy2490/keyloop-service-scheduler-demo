package com.keyloop.scheduler.application.common.result;

import java.util.List;

public record ErrorDetails(List<Error> errors) {

    public ErrorDetails {
        errors = List.copyOf(errors);
    }
}