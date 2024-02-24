package com.prueba.homeworkapp.commons.containers;

import dasniko.testcontainers.keycloak.ExtendableKeycloakContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class TestContainersIntegration {
    @Container
    protected static final PostgreSQLContainer<?> postgreSqlContainer = MockPostgreSqlContainer.getInstance();

    @Container
    protected static final ExtendableKeycloakContainer<?> keycloakContainer = MockKeycloakContainer.getInstance();

    @DynamicPropertySource
    static void registerSettings(DynamicPropertyRegistry registry) {
        postgreSqlContainer.start();

        registry.add(
                "spring.datasource.url",
                postgreSqlContainer::getJdbcUrl
        );
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> keycloakContainer.getAuthServerUrl() + "/realms/test-realm/protocol/openid-connect/certs"
        );
        registry.add(
                "api.keycloak.server-url",
                keycloakContainer::getAuthServerUrl
        );
    }
}
