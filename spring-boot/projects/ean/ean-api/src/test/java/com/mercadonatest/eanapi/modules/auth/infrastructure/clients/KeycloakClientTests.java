package com.mercadonatest.eanapi.modules.auth.infrastructure.clients;

import com.mercadonatest.eanapi.EanApiApplication;
import com.mercadonatest.eanapi.ReplaceUnderscoresAndCamelCase;
import com.mercadonatest.eanapi.annotations.EnabledIfDocker;
import com.mercadonatest.eanapi.containers.MockKeycloakContainer;
import com.mercadonatest.eanapi.containers.MockPostgreSqlContainer;
import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.exceptions.UnauthorizedException;
import dasniko.testcontainers.keycloak.ExtendableKeycloakContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.mercadonatest.eanapi.TestUtils.randomJwt;
import static com.mercadonatest.eanapi.TestUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(EanApiApplication.INT_TEST_PROFILE)
class KeycloakClientTests {

    @Container
    private static final PostgreSQLContainer<?> postgreSqlContainer = MockPostgreSqlContainer.getInstance();

    @Container
    private static final ExtendableKeycloakContainer<?> keycloakContainer = MockKeycloakContainer.getInstance();

    @DynamicPropertySource
    static void registerSettings(DynamicPropertyRegistry registry) {
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

    @Autowired
    private KeycloakClient keycloakClient;

    private static String testUsername = "test";

    private static String testPassword = "test";

    @BeforeAll
    static void createTestUser() {
        final CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(testPassword);

        final UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(testUsername);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        keycloakContainer.getKeycloakAdminClient()
                         .realm("test-realm")
                         .users().create(userRepresentation);
    }

    @Test
    void givenValidCredentials_whenAccess_shouldReturnValidTokenDto() {
        final TokensDto tokensDto = keycloakClient.access(
                testUsername,
                testPassword
        );

        assertNotNull(tokensDto);
        assertNotNull(tokensDto.getAccessToken());
        assertNotNull(tokensDto.getRefreshToken());
    }

    @Test
    void givenInvalidCredentials_whenAccess_shouldThrowUnauthorizedException() {
        final String username = randomText();
        final String password = randomText();

        final UnauthorizedException expectedException = new UnauthorizedException(
                "Invalid user credentials"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.access(username, password)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void thrownUnhandledException_whenAccess_shouldRethrowException() {
        final String username = randomText();
        final String password = randomText();
        final RuntimeException expectedException = new RuntimeException(
                "Invalid user credentials"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.access(username, password)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidRefreshToken_whenRefresh_shouldReturnValidTokenDto() {
        final TokensDto tokensDto = keycloakClient.access(
                testUsername,
                testPassword
        );

        final TokensDto newTokensDto = keycloakClient.refresh(tokensDto.getRefreshToken());

        assertNotNull(newTokensDto);
        assertNotNull(newTokensDto.getAccessToken());
        assertNotNull(newTokensDto.getRefreshToken());
    }

    @Test
    void givenInvalidRefreshToken_whenRefresh_shouldThrowUnauthorizedException() {
        final String inputRefreshToken = randomJwt(randomText());
        final UnauthorizedException expectedException = new UnauthorizedException(
                "Invalid refresh token"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.refresh(inputRefreshToken)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void thrownUnhandledException_whenRefresh_shouldRethrowException() {
        final String inputRefreshToken = randomJwt(randomText());
        final RuntimeException expectedException = new RuntimeException(
                "Invalid refresh token"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.refresh(inputRefreshToken)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidRefreshToken_whenLogout_shouldExecuteProperly() {
        final TokensDto tokensDto = keycloakClient.access(
                testUsername,
                testPassword
        );

        final var response = keycloakClient.logout(
                tokensDto.getRefreshToken()
        );

        assertNull(response);
    }

    @Test
    void givenInvalidRefreshToken_whenLogout_shouldThrowUnauthorizedException() {
        final String inputRefreshToken = randomJwt(randomText());
        final UnauthorizedException expectedException = new UnauthorizedException(
                "Invalid refresh token"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.logout(inputRefreshToken)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void thrownUnhandledException_whenLogout_shouldRethrowException() {
        final String inputRefreshToken = randomJwt(randomText());
        final RuntimeException expectedException = new RuntimeException(
                "Invalid refresh token"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.logout(inputRefreshToken)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
