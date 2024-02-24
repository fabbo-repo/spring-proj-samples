package com.prueba.homeworkapp.commons.clients;

import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;

import java.util.List;
import java.util.UUID;

public interface AuthAdminClient {
    UserRepresentation getUser(final UUID userId);

    List<UserSessionRepresentation> getSessions(final UUID userId);

    UUID createUser(final UserRepresentation user);

    void updateUser(final UserRepresentation userRepresentation);

    void deleteUser(final UUID userId);
}
