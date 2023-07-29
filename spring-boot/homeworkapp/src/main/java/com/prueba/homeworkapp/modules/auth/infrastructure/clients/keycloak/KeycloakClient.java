package com.prueba.homeworkapp.modules.auth.infrastructure.clients.keycloak;

import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.prueba.homeworkapp.core.config.KeycloakProvider;
import com.prueba.homeworkapp.modules.auth.domain.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient implements AuthClient {
    private static final String SCOPE_OPENID = "openid";
    private static final String ACCOUNT_DISABLED_MESSAGE = "Account disabled";

    private final KeycloakProvider keycloakProvider;

    @Override
    public Jwts access(
            final String username,
            final String password
    ) {
        try {
            log.info("Requesting access tokens for user: {}", username);

            final TokenRequest grantAccessRequest = new TokenRequest(
                    keycloakProvider.getTokenEndpoint(),
                    keycloakProvider.getClientAuth(),
                    keycloakProvider.getPasswordGrant(username, password),
                    new Scope(SCOPE_OPENID)
            );

            final TokenResponse tokenResponse = TokenResponse.parse(
                    grantAccessRequest.toHTTPRequest().send()
            );

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

            final TokenRequest refreshTokenRequest = new TokenRequest(
                    keycloakProvider.getTokenEndpoint(),
                    keycloakProvider.getClientAuth(),
                    keycloakProvider.getRefreshTokenGrant(refreshToken)
            );

            final TokenResponse tokenResponse = TokenResponse.parse(
                    refreshTokenRequest.toHTTPRequest().send()
            );

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
    public void logout(final String refreshToken) {
        try {
            log.info("Requesting logout for token {}", refreshToken);

            final TokenRequest logoutRequest = new TokenRequest(
                    keycloakProvider.getLogoutEndpoint(),
                    keycloakProvider.getClientAuth(),
                    keycloakProvider.getRefreshTokenGrant(refreshToken)
            );

            final TokenResponse tokenResponse = TokenResponse.parse(
                    logoutRequest.toHTTPRequest().send()
            );


            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.debug("Successfully logout token {}", refreshToken);
                return;
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

    private Jwts tokenToJwts(final TokenResponse tokenResponse) {
        final Tokens tokens = tokenResponse.toSuccessResponse().getTokens();
        return Jwts.builder()
                   .accessToken(tokens.getAccessToken().getValue())
                   .refreshToken(tokens.getRefreshToken().getValue())
                   .build();
    }
}