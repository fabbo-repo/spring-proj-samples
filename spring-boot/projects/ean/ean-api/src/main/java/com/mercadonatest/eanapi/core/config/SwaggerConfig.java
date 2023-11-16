package com.mercadonatest.eanapi.core.config;

import com.mercadonatest.eanapi.EanApiApplication;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({EanApiApplication.NON_TEST_PROFILE})
public class SwaggerConfig {
    private final static String SWAGGER_TITLE = "Ean API";

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    @Profile(EanApiApplication.NON_TEST_PROFILE)
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().
                                addList("Bearer Authentication")
                )
                .components(
                        new Components()
                                .addSecuritySchemes
                                        ("Bearer Authentication", createAPIKeyScheme())
                )
                .info(
                        new Info()
                                .title(SWAGGER_TITLE)
                );
    }
}
