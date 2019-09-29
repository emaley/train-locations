package com.ewan.timetable.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {
    private String description;
    private String version;

    public ApplicationProperties() {
    }
}