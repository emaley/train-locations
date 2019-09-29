package com.ewan.timetable.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.*;

@ConditionalOnMissingBean(UiConfiguration.class)
@ConditionalOnClass(UiConfiguration.class)
@Configuration
public class SwaggerUIConfigurationAutoConfig {

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .docExpansion(DocExpansion.LIST)
                .operationsSorter(OperationsSorter.ALPHA)
                .defaultModelRendering(ModelRendering.MODEL)
                .build();
    }
}
