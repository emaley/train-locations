package com.ewan.timetable.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Getter
    @Value("${spring.application.name}")
    String applicationName;

    @Getter
    @Autowired
    ApplicationProperties applicationProperties;

    Docket getSwaggerDocket(String basePackage, String title) {
        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo(this.getApiInfo(title, this.getApplicationProperties().getDescription()))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    ApiInfo getApiInfo(String title, String description) {
        return (new ApiInfoBuilder())
                .title(title)
                .description(description)
                .version(this.getApplicationProperties().getVersion())
                .build();
    }

    @Bean
    public Docket api() {
        return this.getSwaggerDocket("com.ewan.timetable", applicationName);
    }
}
