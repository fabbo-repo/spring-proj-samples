package com.prueba.homeworkapp.modules.auth.infrastructure.clients;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.prueba.homeworkapp.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.prueba.homeworkapp.modules.auth.infrastructure.providers.KeycloakProvider;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.prueba.homeworkapp.TestUtils.randomJwt;
import static com.prueba.homeworkapp.TestUtils.randomLong;
import static com.prueba.homeworkapp.TestUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@RunWith(MockitoJUnitRunner.class)
class KeycloakClientTest {
    @Mock
    private KeycloakProvider keycloakProvider;

    @InjectMocks
    private KeycloakClient keycloakClient;

    @Mock
    private TokenResponse tokenResponseMock;

    @Mock
    private TokenErrorResponse tokenErrorResponseMock;

    @Mock
    private Tokens tokensMock;

    @Mock
    private ErrorObject errorObjectMock;

    @Mock
    private AccessToken accessTokenMock;

    @Mock
    private RefreshToken refreshTokenMock;

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
        final String username = randomText();
        final String password = randomText();
        final String expectedAccessToken = randomJwt(randomText());
        final String expectedRefreshToken = randomJwt(randomText());
        final long expectedLifetime = randomLong(0, 300);

        when(tokenResponseMock.toHTTPResponse())
                .thenReturn(new HTTPResponse(HttpStatus.OK.value()));
        when(accessTokenMock.getValue()).thenReturn(expectedAccessToken);
        when(accessTokenMock.getLifetime()).thenReturn(expectedLifetime);
        when(refreshTokenMock.getValue()).thenReturn(expectedRefreshToken);
        when(tokensMock.getAccessToken()).thenReturn(accessTokenMock);
        when(tokensMock.getRefreshToken()).thenReturn(refreshTokenMock);
        when(tokenResponseMock.toSuccessResponse())
                .thenReturn(new AccessTokenResponse(tokensMock));
        when(keycloakProvider.getAccessToken(username, password))
                .thenReturn(tokenResponseMock);

        final Jwts tokensDto = keycloakClient.access(username, password);

        assertEquals(expectedAccessToken, tokensDto.getAccessToken());
        assertEquals(expectedRefreshToken, tokensDto.getRefreshToken());
        assertEquals(expectedLifetime, tokensDto.getAccessExpiresIn());
    }

    @Test
    void givenInvalidCredentials_whenAccess_shouldThrowUnauthorizedException()
            throws URISyntaxException, IOException, ParseException {
        final String username = randomText();
        final String password = randomText();
        final UnauthorizedException expectedException = new UnauthorizedException(
                randomText()
        );

        when(tokenResponseMock.toHTTPResponse())
                .thenReturn(new HTTPResponse(HttpStatus.BAD_REQUEST.value()));
        when(errorObjectMock.getHTTPStatusCode()).thenReturn(HttpStatus.BAD_REQUEST.value());
        when(errorObjectMock.getDescription()).thenReturn(expectedException.getMessage());
        when(tokenErrorResponseMock.getErrorObject()).thenReturn(errorObjectMock);
        when(tokenResponseMock.toErrorResponse())
                .thenReturn(tokenErrorResponseMock);
        when(keycloakProvider.getAccessToken(username, password))
                .thenReturn(tokenResponseMock);

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.access(username, password)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void thrownUnhandledException_whenAccess_shouldRethrowException()
            throws URISyntaxException, IOException, ParseException {
        final String username = randomText();
        final String password = randomText();
        final RuntimeException expectedException = new RuntimeException();

        when(keycloakProvider.getAccessToken(username, password))
                .thenThrow(expectedException);

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.access(username, password)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidRefreshToken_whenRefresh_shouldReturnValidTokenDto()
            throws URISyntaxException, IOException, ParseException {
        final String inputRefreshToken = randomJwt(randomText());
        final String expectedAccessToken = randomJwt(randomText());
        final String expectedRefreshToken = randomJwt(randomText());
        final long expectedLifetime = randomLong(0, 300);

        when(tokenResponseMock.toHTTPResponse())
                .thenReturn(new HTTPResponse(HttpStatus.OK.value()));
        when(accessTokenMock.getValue()).thenReturn(expectedAccessToken);
        when(accessTokenMock.getLifetime()).thenReturn(expectedLifetime);
        when(refreshTokenMock.getValue()).thenReturn(expectedRefreshToken);
        when(tokensMock.getAccessToken()).thenReturn(accessTokenMock);
        when(tokensMock.getRefreshToken()).thenReturn(refreshTokenMock);
        when(tokenResponseMock.toSuccessResponse())
                .thenReturn(new AccessTokenResponse(tokensMock));
        when(keycloakProvider.getNewRefreshToken(inputRefreshToken))
                .thenReturn(tokenResponseMock);

        final Jwts jwts = keycloakClient.refresh(inputRefreshToken);

        assertEquals(expectedAccessToken, jwts.getAccessToken());
        assertEquals(expectedRefreshToken, jwts.getRefreshToken());
        assertEquals(expectedLifetime, jwts.getAccessExpiresIn());
    }

    @Test
    void givenInvalidRefreshToken_whenRefresh_shouldThrowUnauthorizedException()
            throws URISyntaxException, IOException, ParseException {
        final String inputRefreshToken = randomJwt(randomText());
        final UnauthorizedException expectedException = new UnauthorizedException(
                randomText()
        );

        when(tokenResponseMock.toHTTPResponse())
                .thenReturn(new HTTPResponse(HttpStatus.BAD_REQUEST.value()));
        when(errorObjectMock.getHTTPStatusCode()).thenReturn(HttpStatus.BAD_REQUEST.value());
        when(errorObjectMock.getDescription()).thenReturn(expectedException.getMessage());
        when(tokenErrorResponseMock.getErrorObject()).thenReturn(errorObjectMock);
        when(tokenResponseMock.toErrorResponse())
                .thenReturn(tokenErrorResponseMock);
        when(keycloakProvider.getNewRefreshToken(inputRefreshToken))
                .thenReturn(tokenResponseMock);

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
        final RuntimeException expectedException = new RuntimeException();

        when(keycloakProvider.getNewRefreshToken(inputRefreshToken))
                .thenThrow(expectedException);

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

        when(tokenResponseMock.toHTTPResponse())
                .thenReturn(new HTTPResponse(HttpStatus.OK.value()));
        when(keycloakProvider.getLogoutToken(inputRefreshToken))
                .thenReturn(tokenResponseMock);

        final var response = keycloakClient.logout(inputRefreshToken);

        assertNull(response);
    }

    @Test
    void givenInvalidRefreshToken_whenLogout_shouldThrowUnauthorizedException()
            throws URISyntaxException, IOException, ParseException {
        final String inputRefreshToken = randomJwt(randomText());
        final UnauthorizedException expectedException = new UnauthorizedException(
                randomText()
        );

        when(tokenResponseMock.toHTTPResponse())
                .thenReturn(new HTTPResponse(HttpStatus.BAD_REQUEST.value()));
        when(errorObjectMock.getHTTPStatusCode()).thenReturn(HttpStatus.BAD_REQUEST.value());
        when(errorObjectMock.getDescription()).thenReturn(expectedException.getMessage());
        when(tokenErrorResponseMock.getErrorObject()).thenReturn(errorObjectMock);
        when(tokenResponseMock.toErrorResponse())
                .thenReturn(tokenErrorResponseMock);
        when(keycloakProvider.getLogoutToken(inputRefreshToken))
                .thenReturn(tokenResponseMock);

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
        final RuntimeException expectedException = new RuntimeException();

        when(keycloakProvider.getLogoutToken(inputRefreshToken))
                .thenThrow(expectedException);

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> keycloakClient.logout(inputRefreshToken)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}