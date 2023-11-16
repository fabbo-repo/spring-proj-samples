package com.mercadonatest.eanapi.core.config;


import com.nimbusds.oauth2.sdk.RefreshTokenGrant;
import com.nimbusds.oauth2.sdk.ResourceOwnerPasswordCredentialsGrant;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Getter
@Configuration
public class KeycloakConfig {

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
}
