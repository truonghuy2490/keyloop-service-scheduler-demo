package com.keyloop.scheduler.presentation.config.cors;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;

@Validated
@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties(
    @NotEmpty
    List<String> allowedOrigins
) {
}