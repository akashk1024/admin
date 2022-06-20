package com.greatlearning.microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select().apis(predicate)
                .paths(selectors)
                .build();
        return myProjectDocket;
    }

    @Bean
    public ApiInfo myApiInfo() {
        return new ApiInfoBuilder().title(title).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "overall access");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

}
