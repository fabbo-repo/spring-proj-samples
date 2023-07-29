package com.prueba.homeworkapp.modules.auth.infrastructure.clients.keycloak;

import com.prueba.homeworkapp.core.config.KeycloakProvider;
import com.prueba.homeworkapp.modules.auth.domain.clients.AuthAdminClient;
import com.prueba.homeworkapp.modules.user.domain.models.exceptions.CannotCreateUserException;
import com.prueba.homeworkapp.modules.user.domain.models.exceptions.CannotDeleteUserException;
import com.prueba.homeworkapp.modules.user.domain.models.exceptions.UserAlreadyExistsException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminClient implements AuthAdminClient {

    private final KeycloakProvider keycloakProvider;

    @Override
    public UserRepresentation getUser(final UUID userId) {
        log.info("Getting data for user {} in Keycloak realm {}",
                 userId, keycloakProvider.getRealm()
        );

        final UsersResource usersResource = keycloakProvider.getUsersResource();
        final UserResource userResource = usersResource.get(userId.toString());
        return userResource.toRepresentation();
    }

    @Override
    public List<UserSessionRepresentation> getSessions(final UUID userId) {
        log.info("Getting data for user {} in Keycloak realm {}",
                 userId, keycloakProvider.getRealm()
        );

        final UsersResource usersResource = keycloakProvider.getUsersResource();
        final UserResource userResource = usersResource.get(userId.toString());
        return userResource.getUserSessions();
    }

    @Override
    public UUID createUser(final UserRepresentation user) {
        log.info("Creating user {} in Keycloak realm {}",
                 user.getUsername(), keycloakProvider.getRealm()
        );

        final UsersResource usersResource = keycloakProvider.getUsersResource();
        final Response response = usersResource.create(user);
        final Response.StatusType responseStatusInfo = response.getStatusInfo();

        if (Response.Status.CONFLICT.equals(responseStatusInfo)) {
            throw new UserAlreadyExistsException(user.getEmail());
        } else if (!Response.Status.CREATED.equals(responseStatusInfo)) {
            log.warn(
                    "Unexpected result while trying to create user: {}, status: {}, reason: {}",
                    user.getEmail(),
                    responseStatusInfo.getStatusCode(),
                    responseStatusInfo.getReasonPhrase()
            );
            throw new CannotCreateUserException(user.getEmail());
        }

        final String userId = CreatedResponseUtil.getCreatedId(response);
        log.info("User {} created with id {}", user.getEmail(), userId);

        return UUID.fromString(userId);
    }

    @Override
    public void updateUser(
            final UserRepresentation userRepresentation
    ) {
        final UUID userId = UUID.fromString(userRepresentation.getId());
        log.info("Updating user {} in Keycloak realm {}",
                 userId, keycloakProvider.getRealm()
        );
        final UsersResource usersResource = keycloakProvider.getUsersResource();
        final UserResource userResource = usersResource.get(userId.toString());
        userResource.update(userRepresentation);
    }

    @Override
    public void deleteUser(final UUID userId) {
        log.info("Deleting user {} in Keycloak realm {}",
                 userId, keycloakProvider.getRealm()
        );
        final UsersResource usersResource = keycloakProvider.getUsersResource();
        final Response response = usersResource.delete(userId.toString());
        final Response.StatusType responseStatusInfo = response.getStatusInfo();

        if (!Response.Status.NO_CONTENT.equals(responseStatusInfo)) {
            log.warn(
                    "Unexpected result while trying to delete user: {}, status: {}, reason: {}",
                    userId,
                    responseStatusInfo.getStatusCode(),
                    responseStatusInfo.getReasonPhrase()
            );
            throw new CannotDeleteUserException(userId);
        }
    }
}
