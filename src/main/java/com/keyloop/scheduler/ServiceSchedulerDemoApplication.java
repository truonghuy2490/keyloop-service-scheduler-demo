package com.keyloop.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ServiceSchedulerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            ServiceSchedulerDemoApplication.class,
            args
        );
    }
}