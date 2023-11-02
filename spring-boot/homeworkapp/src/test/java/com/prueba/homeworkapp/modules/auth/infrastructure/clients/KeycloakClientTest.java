package com.prueba.homeworkapp.modules.auth.infrastructure.clients;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.annotations.EnabledIfDocker;
import com.prueba.homeworkapp.containers.MockKeycloakContainer;
import com.prueba.homeworkapp.containers.MockPostgreSqlContainer;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.exceptions.UnauthorizedException;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import static com.prueba.homeworkapp.TestUtils.randomEmail;
import static com.prueba.homeworkapp.TestUtils.randomJwt;
import static com.prueba.homeworkapp.TestUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(HomeworkappApplication.INT_TEST_PROFILE)
class KeycloakClientTest {

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

    private static String testEmail = "test@mail.com";

    private static String testPassword = "testPassword";

    @BeforeAll
    static void createTestUser() {
        final CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(testPassword);

        final UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(testEmail);
        userRepresentation.setUsername(testEmail);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        keycloakContainer.getKeycloakAdminClient()
                         .realm("test-realm")
                         .users().create(userRepresentation);
    }

    @Test
    void checkDecodeTokenMethod_withRandomJwt() {
        final String expectedSubject = randomText();
        final String token = randomJwt(expectedSubject);

        final JsonObject jsonObject = keycloakClient.decodeToken(token);

        assertEquals(expectedSubject, jsonObject.get("sub").toString().replaceAll("\"", ""));
    }

    @Test
    void givenValidCredentials_whenAccess_shouldReturnValidTokenDto()
            throws URISyntaxException, IOException, ParseException {
        final Jwts tokensDto = keycloakClient.access(
                testEmail,
                testPassword
        );

        assertNotNull(tokensDto);
        assertNotNull(tokensDto.getAccessToken());
        assertNotNull(tokensDto.getRefreshToken());
    }

    @Test
    void givenInvalidCredentials_whenAccess_shouldThrowUnauthorizedException()
            throws URISyntaxException, IOException, ParseException {
        final String email = randomEmail();
        final String password = randomText();

        final UnauthorizedException expectedException = new UnauthorizedException(
                "Invalid user credentials"
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.access(email, password)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void thrownUnhandledException_whenAccess_shouldRethrowException()
            throws URISyntaxException, IOException, ParseException {
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
    void givenValidRefreshToken_whenRefresh_shouldReturnValidTokenDto()
            throws URISyntaxException, IOException, ParseException {
        final Jwts tokensDto = keycloakClient.access(
                testEmail,
                testPassword
        );

        final Jwts jwts = keycloakClient.refresh(tokensDto.getRefreshToken());

        assertNotNull(jwts);
        assertNotNull(jwts.getAccessToken());
        assertNotNull(jwts.getRefreshToken());
    }

    @Test
    void givenInvalidRefreshToken_whenRefresh_shouldThrowUnauthorizedException()
            throws URISyntaxException, IOException, ParseException {
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
    void thrownUnhandledException_whenRefresh_shouldRethrowException()
            throws URISyntaxException, IOException, ParseException {
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
    void givenValidRefreshToken_whenLogout_shouldExecuteProperly()
            throws URISyntaxException, IOException, ParseException {
        final String inputRefreshToken = randomJwt(randomText());

        final Jwts jwts = keycloakClient.access(
                testEmail,
                testPassword
        );

        final var response = keycloakClient.logout(
                jwts.getRefreshToken()
        );

        assertNull(response);
    }

    @Test
    void givenInvalidRefreshToken_whenLogout_shouldThrowUnauthorizedException()
            throws URISyntaxException, IOException, ParseException {
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
    void thrownUnhandledException_whenLogout_shouldRethrowException()
            throws URISyntaxException, IOException, ParseException {
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