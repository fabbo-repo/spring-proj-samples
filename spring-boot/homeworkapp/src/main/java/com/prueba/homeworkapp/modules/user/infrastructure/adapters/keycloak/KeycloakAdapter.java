package com.prueba.homeworkapp.modules.user.infrastructure.adapters.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdapter {
    /*
    public AccessTokenResponse login(
            final String username,
            final String password
    ) {
        try {
            log.info("Attempting authorization request on behalf of user {}", username);

            TokenRequest grantAccessRequest = new TokenRequest(
                    prepareEndpointUri(TOKEN_ENDPOINT),
                    prepareClientAuth(),
                    preparePasswordGrant(username, password),
                    new Scope(SCOPE_OPENID)
            );

            TokenResponse tokenResponse = TokenResponse.parse(grantAccessRequest.toHTTPRequest().send());

            if (tokenResponse.toHTTPResponse().indicatesSuccess()) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Successfully authorized user {}", username);
                }
                return KeycloakResponse.ok(tokenResponse.toSuccessResponse().toJSONObject());
            }

            ErrorObject errorObject = tokenResponse.toErrorResponse().getErrorObject();

            if (ACCOUNT_DISABLED_MESSAGE.equals(errorObject.getDescription())) {
                LOGGER.error(
                        "Error while attempting authorization request on behalf of user {} : {} {}",
                        username,
                        errorObject.getHTTPStatusCode(),
                        errorObject.getDescription()
                );

                return KeycloakResponse.accountDisabled();
            }

            LOGGER.error(
                    "Error while attempting authorization request on behalf of user {} : {} {}",
                    username,
                    errorObject.getHTTPStatusCode(),
                    errorObject.getDescription()
            );

            return KeycloakResponse.error(errorObject.getHTTPStatusCode(), errorObject.getDescription());
        } catch (URISyntaxException | IllegalArgumentException e) {
            log.error(
                    "Error while handling authentication for user {} : {}",
                    username,
                    e.getMessage()
            );
            return KeycloakResponse.badRequest();
        } catch (ParseException | IOException e) {
            log.info(
                    "Error while handling authentication for user {} : {}",
                    username,
                    e.getMessage()
            );
            return KeycloakResponse.unexpected();
        }
    }

    public ResponseEntity<ApiOperationResult> refresh(
            @CookieValue(name = OAuth2ParameterNames.REFRESH_TOKEN) String refreshToken
    ) {
        StopWatch timer = new StopWatch();
        timer.start();
        if (log.isTraceEnabled()) {
            log.trace("Starting refresh operation for token {}", refreshToken);
        }

        try {
            KeycloakResponse response = keycloakAuthenticator.refreshToken(
                    new KeycloakRefreshTokenRequest(refreshToken));

            if (response.isSuccess()) {
                KeycloakTokenResponse refreshTokenResponse = KeycloakTokenResponse.Utils.parse(response);
                DecodedToken decodedToken = keycloakAuthenticator.decodeToken(refreshTokenResponse.accessToken());
                UUID id = keycloakAuthenticator.getProfileByMail(decodedToken.email());

                timer.stop();
                if (log.isTraceEnabled()) {
                    log.trace("Ended refresh operation for token {} in {}ms",
                              refreshToken, timer.getTotalTimeMillis()
                    );
                }
                return ResponseEntity.ok()
                                     .header(HttpHeaders.SET_COOKIE, generateRefreshTokenCookie(refreshTokenResponse))
                                     .body(LoginResponse
                                                   .builder()
                                                   .id(id)
                                                   .name(decodedToken.name())
                                                   .username(decodedToken.username())
                                                   .given_name(decodedToken.given_name())
                                                   .family_name(decodedToken.family_name())
                                                   .email(decodedToken.email())
                                                   .accessToken(refreshTokenResponse.accessToken())
                                                   .refreshToken(refreshTokenResponse.refreshToken())
                                                   .build());
            }

            timer.stop();
            if (log.isTraceEnabled()) {
                log.error("Failed to refresh token {} in {}ms.", refreshToken, timer.getTotalTimeMillis());
            }
            return ProblemDetails.status(response.httpStatusCode())
                                 .withTitle("Could not refresh access")
                                 .withDetail(response.message())
                                 .asResponseEntity();

        } catch (Exception e) {
            timer.stop();
            if (log.isTraceEnabled()) {
                log.error("Failed to refresh token {} in {}ms. {}",
                          refreshToken, timer.getTotalTimeMillis(), e.getMessage()
                );
            }
            return ProblemDetails.internalServerError()
                                 .withDetail(e.getMessage())
                                 .asResponseEntity();
        }
    }

    public ResponseEntity<ApiOperationResult> logout(
            @CookieValue(name = OAuth2ParameterNames.REFRESH_TOKEN) String refreshToken,
            Authentication authentication
    ) {
        StopWatch timer = new StopWatch();
        String subject = ((Jwt) authentication.getPrincipal()).getSubject();
        timer.start();
        log.info("Starting logout operation for user {}", subject);

        try {
            KeycloakResponse response = keycloakAuthenticator.logout(
                    new KeycloakLogoutRequest(refreshToken));

            if (response.isSuccess()) {
                timer.stop();
                log.info("Ended logout operation for user {} in {}ms", subject, timer.getTotalTimeMillis());
                return ResponseEntity.noContent().build();
            }

            timer.stop();
            log.error("Failed to logout user {} in {}ms.", subject, timer.getTotalTimeMillis());
            return ProblemDetails.status(response.httpStatusCode())
                                 .withTitle("Could not logout")
                                 .withDetail(response.message())
                                 .asResponseEntity();

        } catch (Exception e) {
            timer.stop();
            log.error("Failed to logout user {} in {}ms. {}", subject, timer.getTotalTimeMillis(), e.getMessage());
            return ProblemDetails.internalServerError()
                                 .withDetail(e.getMessage())
                                 .asResponseEntity();
        }
    }*/
}
