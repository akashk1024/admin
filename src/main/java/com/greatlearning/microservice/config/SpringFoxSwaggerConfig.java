package com.greatlearning.microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

@Configuration
public class SpringFoxSwaggerConfig {

    @Value("${spring.application.name}")
    private String title;

    @Bean
    public Docket api() {
        Predicate<RequestHandler> predicate =
                RequestHandlerSelectors.basePackage("com.greatlearning.microservice");
        Predicate<String> selectors = PathSelectors.any();
        Docket myProjectDocket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(myApiInfo())
                .groupName("Surabhi Restaurant Users API group")
                .select().apis(predicate)
                .paths(selectors)
                .build();
        return myProjectDocket;
    }

    @Bean
    public ApiInfo myApiInfo() {
        return new ApiInfoBuilder().title(title).build();
    }


}
