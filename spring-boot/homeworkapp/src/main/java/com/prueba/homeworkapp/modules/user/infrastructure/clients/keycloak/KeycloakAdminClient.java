package com.prueba.homeworkapp.modules.user.infrastructure.clients.keycloak;

import com.prueba.homeworkapp.core.config.KeycloakProvider;
import com.prueba.homeworkapp.modules.user.domain.models.exceptions.CannotCreateUserException;
import com.prueba.homeworkapp.modules.user.domain.models.exceptions.UserAlreadyExistsException;
import com.prueba.homeworkapp.modules.user.domain.models.requests.UserRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminClient {
    private static final boolean USER_ENABLED = true;
    private static final boolean USER_EMAIL_VERIFIED = true;
    private static final String USER_CREDENTIALS_TYPE = "password";
    private static final boolean USER_CREDENTIALS_TEMPORARY = false;
    private static final String USER_REQUIRED_ACTION = "UPDATE_PASSWORD";
    private static final int USER_UPDATE_PASSWORD_TOKEN_LIFESPAN = 86400;
    private static final String DEFAULT_ROLES_PREFIX = "default-roles-";

    private final KeycloakProvider keycloakProvider;

    // TODO get user data

    // TODO get sessions

    public UUID createUser(final UserRequest request) {
        final Keycloak keycloak = keycloakProvider.getInstance();

        log.info("Creating user {} in Keycloak realm {}", request.username(), keycloakProvider.getRealm());

        final UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setEmailVerified(USER_EMAIL_VERIFIED);
        user.setEnabled(USER_ENABLED);

        final CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setTemporary(USER_CREDENTIALS_TEMPORARY);
        credentials.setType(USER_CREDENTIALS_TYPE);
        credentials.setValue(request.password());

        user.setCredentials(Collections.singletonList(credentials));

        final UsersResource realmUsers = keycloak.realm(keycloakProvider.getRealm()).users();

        final Response response = realmUsers.create(user);

        final Response.StatusType responseStatusInfo = response.getStatusInfo();

        if (Response.Status.CONFLICT.equals(responseStatusInfo)) {
            log.info("User {} already exists", request.email());
            throw new UserAlreadyExistsException(request.email());
        } else if (!Response.Status.CREATED.equals(responseStatusInfo)) {
            log.warn("Unexpected result while trying to create user: {}, status: {}, reason: {}",
                     request.email(), responseStatusInfo.getStatusCode(), responseStatusInfo.getReasonPhrase()
            );
            throw new CannotCreateUserException(request.email());
        }

        final String userId = CreatedResponseUtil.getCreatedId(response);

        log.info("User {} created with id {}", request.email(), userId);
        return UUID.fromString(userId);
    }

    // TODO update user

    // TODO delete user
}
