package com.prueba.homeworkapp.modules.auth.infrastructure.providers;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.ResourceOwnerPasswordCredentialsGrant;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.Getter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.keycloak.OAuth2Constants.SCOPE_OPENID;

@Getter
@Configuration
public class KeycloakProvider {

    private static final String TOKEN_ENDPOINT = "/protocol/openid-connect/token";
    private static final String LOGOUT_ENDPOINT = "/protocol/openid-connect/logout";

    @Value("${api.keycloak.server-url}")
    private String serverUrl;

    @Value("${api.keycloak.realm}")
    private String realm;

    @Value("${api.keycloak.client-id}")
    private String clientId;

    @Value("${api.keycloak.secret}")
    private String secret;

    private Keycloak keycloak = null;

    public Keycloak getInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder
                    .builder()
                    .realm(realm)
                    .serverUrl(serverUrl)
                    .clientId(clientId)
                    .clientSecret(secret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
        return keycloak;
    }

    public UsersResource getUsersResource() {
        return getInstance().realm(realm).users();
    }

    public URI getTokenEndpoint() throws URISyntaxException {
        return new URI(
                serverUrl
                        .concat("/realms/")
                        .concat(realm)
                        .concat(TOKEN_ENDPOINT));
    }

    public URI getLogoutEndpoint() throws URISyntaxException {
        return new URI(
                serverUrl
                        .concat("/realms/")
                        .concat(realm)
                        .concat(LOGOUT_ENDPOINT));
    }

    public ClientSecretBasic getClientAuth() {
        return new ClientSecretBasic(
                new ClientID(clientId),
                new Secret(secret)
        );
    }

    public RefreshTokenGrant getRefreshTokenGrant(final String refreshToken) {
        return new RefreshTokenGrant(
                new RefreshToken(refreshToken)
        );
    }

    public ResourceOwnerPasswordCredentialsGrant getPasswordGrant(
            final String username,
            final String password
    ) {
        return new ResourceOwnerPasswordCredentialsGrant(
                username,
                new Secret(password)
        );
    }

    public TokenResponse getAccessToken(final String username, final String password)
            throws URISyntaxException, IOException, ParseException {
        return TokenResponse.parse(new TokenRequest(
                getTokenEndpoint(),
                getClientAuth(),
                getPasswordGrant(username, password),
                new Scope(SCOPE_OPENID)
        ).toHTTPRequest().send());
    }

    public TokenResponse getNewRefreshToken(final String refreshToken)
            throws URISyntaxException, IOException, ParseException {
        return TokenResponse.parse(new TokenRequest(
                getTokenEndpoint(),
                getClientAuth(),
                getRefreshTokenGrant(refreshToken)
        ).toHTTPRequest().send());
    }

    public TokenResponse getLogoutToken(final String refreshToken)
            throws URISyntaxException, IOException, ParseException {
        return TokenResponse.parse(new TokenRequest(
                getLogoutEndpoint(),
                getClientAuth(),
                getRefreshTokenGrant(refreshToken)
        ).toHTTPRequest().send());
    }
}