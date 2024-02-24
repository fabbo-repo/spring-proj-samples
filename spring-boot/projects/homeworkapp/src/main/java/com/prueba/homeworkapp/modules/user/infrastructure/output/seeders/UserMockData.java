package com.prueba.homeworkapp.modules.user.infrastructure.output.seeders;

import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMockData {
    public static final List<UserJpaEntity> dbData = List.of(
            new UserJpaEntity(
                    null,
                    "random",
                    "random@random.com",
                    "Random",
                    "Random",
                    20
            )
    );

    public static final List<UserRepresentation> authData = List.of(
            createUserRepresentation(
                    "random@random.org",
                    "Random",
                    "Random",
                    "random"
            )
    );

    public static UserRepresentation createUserRepresentation(
            final String email,
            final String name,
            final String surname,
            final String password
    ) {
        final CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        final UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(email);
        userRepresentation.setUsername(email);
        userRepresentation.setFirstName(name);
        userRepresentation.setLastName(surname);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        return userRepresentation;
    }
}
