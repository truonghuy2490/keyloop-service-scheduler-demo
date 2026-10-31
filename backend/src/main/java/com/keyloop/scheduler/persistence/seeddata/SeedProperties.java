package com.keyloop.scheduler.persistence.seeddata;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.seed")
public record SeedProperties(
    boolean enabled
) {
}