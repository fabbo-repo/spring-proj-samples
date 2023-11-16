package com.mercadonatest.eanapi.modules.auth.infrastructure.clients;

import com.mercadonatest.eanapi.core.config.KeycloakConfig;
import com.mercadonatest.eanapi.modules.auth.domain.clients.AuthClient;
import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.keycloak.OAuth2Constants.SCOPE_OPENID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient implements AuthClient {
    private final KeycloakConfig keycloakConfig;

    @Override
    public TokensDto access(
            final String username,
            final String password
    ) {
        try {
            final TokenResponse tokenResponse = getAccessToken(
                    username,
                    password
            );

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.info("Successfully authorized user {}", username);
                return tokenToDto(tokenResponse);
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

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
    public TokensDto refresh(final String refreshToken) {
        try {
            final TokenResponse tokenResponse = getNewRefreshToken(refreshToken);

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.info("Successfully refreshed access for token {}", refreshToken);
                return tokenToDto(tokenResponse);
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

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
            final TokenResponse tokenResponse = getLogoutToken(
                    refreshToken
            );

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.info("Successfully logout token {}", refreshToken);
                return null;
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

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

    private TokensDto tokenToDto(final TokenResponse tokenResponse) {
        final Tokens tokens = tokenResponse.toSuccessResponse().getTokens();
        final AccessToken accessTokenObj = tokens.getAccessToken();
        final String refreshToken = tokens.getRefreshToken().getValue();

        return TokensDto
                .builder()
                .accessToken(accessTokenObj.getValue())
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse getAccessToken(final String username, final String password)
            throws URISyntaxException, IOException, ParseException {
        return TokenResponse.parse(new TokenRequest(
                keycloakConfig.getTokenEndpoint(),
                keycloakConfig.getClientAuth(),
                keycloakConfig.getPasswordGrant(username, password),
                new Scope(SCOPE_OPENID)
        ).toHTTPRequest().send());
    }

    public TokenResponse getNewRefreshToken(final String refreshToken)
            throws URISyntaxException, IOException, ParseException {
        return TokenResponse.parse(new TokenRequest(
                keycloakConfig.getTokenEndpoint(),
                keycloakConfig.getClientAuth(),
                keycloakConfig.getRefreshTokenGrant(refreshToken)
        ).toHTTPRequest().send());
    }

    public TokenResponse getLogoutToken(final String refreshToken)
            throws URISyntaxException, IOException, ParseException {
        return TokenResponse.parse(new TokenRequest(
                keycloakConfig.getLogoutEndpoint(),
                keycloakConfig.getClientAuth(),
                keycloakConfig.getRefreshTokenGrant(refreshToken)
        ).toHTTPRequest().send());
    }
}
