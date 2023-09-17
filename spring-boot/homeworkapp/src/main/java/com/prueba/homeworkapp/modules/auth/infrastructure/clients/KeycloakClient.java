package com.prueba.homeworkapp.modules.auth.infrastructure.clients;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.prueba.homeworkapp.modules.auth.domain.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.prueba.homeworkapp.modules.auth.infrastructure.providers.KeycloakProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient implements AuthClient {
    private final KeycloakProvider keycloakProvider;

    @Override
    public Jwts access(
            final String username,
            final String password
    ) {
        try {
            log.info("Requesting access tokens for user: {}", username);

            final TokenResponse tokenResponse = keycloakProvider.getAccessToken(username, password);

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.debug("Successfully authorized user {}", username);
                return tokenToJwts(tokenResponse);
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

            log.error(
                    "Unauthorized request for user {} : {} {}",
                    username,
                    errorObject.getHTTPStatusCode(),
                    errorObject.getDescription()
            );

            throw new UnauthorizedException(errorObject.getDescription());
        } catch (URISyntaxException | IllegalArgumentException | ParseException |
                 IOException ex) {
            log.error(
                    "Error handling authentication for user {} : {}",
                    username,
                    ex.getMessage()
            );
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Jwts refresh(final String refreshToken) {
        try {
            log.info("Requesting access token for refresh token {}", refreshToken);

            final TokenResponse tokenResponse = keycloakProvider.getNewRefreshToken(refreshToken);

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.debug("Successfully refreshed access for token {}", refreshToken);
                return tokenToJwts(tokenResponse);
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

            log.error(
                    "Unauthorized refresh request : {} {}",
                    errorObject.getHTTPStatusCode(),
                    errorObject.getDescription()
            );

            throw new UnauthorizedException(errorObject.getDescription());
        } catch (URISyntaxException | IllegalArgumentException | ParseException |
                 IOException ex) {
            log.error(
                    "Error handling refresh : {}",
                    ex.getMessage()
            );
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Void logout(final String refreshToken) {
        try {
            log.info("Requesting logout for token {}", refreshToken);

            final TokenResponse tokenResponse = keycloakProvider.getLogoutToken(
                    refreshToken
            );

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.debug("Successfully logout token {}", refreshToken);
                return null;
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

            log.error(
                    "Unauthorized refresh request : {} {}",
                    errorObject.getHTTPStatusCode(),
                    errorObject.getDescription()
            );

            throw new UnauthorizedException(errorObject.getDescription());
        } catch (URISyntaxException | IllegalArgumentException | ParseException |
                 IOException ex) {
            log.error(
                    "Error handling refresh : {}",
                    ex.getMessage()
            );
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JsonObject decodeToken(final String token) {
        final String[] chunks = token.split("\\.");
        final Base64.Decoder decoder = Base64.getUrlDecoder();
        final String payload = new String(decoder.decode(chunks[1]), StandardCharsets.UTF_8);
        return JsonParser.parseString(payload).getAsJsonObject();
    }

    private Jwts tokenToJwts(final TokenResponse tokenResponse) {
        final Tokens tokens = tokenResponse.toSuccessResponse().getTokens();
        final AccessToken accessTokenObj = tokens.getAccessToken();

        final String refreshToken = tokens.getRefreshToken().getValue();
        final long iat = decodeToken(refreshToken).get("iat").getAsLong();
        final long exp = decodeToken(refreshToken).get("exp").getAsLong();

        return Jwts
                .builder()
                .accessToken(accessTokenObj.getValue())
                .refreshToken(refreshToken)
                .accessExpiresIn(accessTokenObj.getLifetime())
                .refreshExpiresIn(exp - iat)
                .build();
    }
}