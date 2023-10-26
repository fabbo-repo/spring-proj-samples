package com.prueba.homeworkapp.modules.auth.infrastructure.clients;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import com.prueba.homeworkapp.core.config.KeycloakConfig;
import com.prueba.homeworkapp.modules.auth.domain.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.keycloak.OAuth2Constants.SCOPE_OPENID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient implements AuthClient {
    private final KeycloakConfig keycloakConfig;

    @Override
    public Jwts access(
            final String email,
            final String password
    ) {
        try {
            final TokenResponse tokenResponse = getAccessToken(
                    email,
                    password
            );

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.info("Successfully authorized user {}", email);
                return tokenToJwts(tokenResponse);
            }

            final ErrorObject errorObject = tokenResponse
                    .toErrorResponse()
                    .getErrorObject();

            throw new UnauthorizedException(errorObject.getDescription());
        } catch (URISyntaxException | IllegalArgumentException | ParseException |
                 IOException ex) {
            log.error(
                    "Error handling authentication for user {} : {}",
                    email,
                    ex.getMessage()
            );
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Jwts refresh(final String refreshToken) {
        try {
            final TokenResponse tokenResponse = getNewRefreshToken(refreshToken);

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                log.info("Successfully refreshed access for token {}", refreshToken);
                return tokenToJwts(tokenResponse);
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