package com.salesflow.plan_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String AUTH_SCHEME = "Authorization";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plan Service API")
                        .description("Plan - Plan Service")
                        .version("v1")
                )
                .addSecurityItem(
                        new SecurityRequirement().addList(AUTH_SCHEME)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        AUTH_SCHEME,
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(SecurityScheme.In.HEADER)
                                                .description("API Key no header Authorization")
                                )
                );
    }
}
