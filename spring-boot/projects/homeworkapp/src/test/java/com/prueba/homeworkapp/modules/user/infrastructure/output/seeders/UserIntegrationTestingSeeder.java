package com.prueba.homeworkapp.modules.user.infrastructure.output.seeders;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.commons.config.KeycloakConfig;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.infrastructure.output.repositories.jpa.UserJpaRepository;
import com.prueba.homeworkapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntityFactory;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomText;

@RequiredArgsConstructor
@Profile(HomeworkappApplication.INT_TEST_PROFILE)
@Component
@Slf4j
@Getter
public class UserIntegrationTestingSeeder {

    private final List<UserJpaEntity> userJpaEntities = new ArrayList<>();

    private final UserJpaRepository userJpaRepository;

    private final KeycloakConfig keycloakConfig;

    private final String testPassword = randomText();

    public void insertData() {
        log.info("Creating User Integration Testing Data...");
        for (int i = 0; i < 3; i++) {
            final UserJpaEntity userJpaEntity = UserJpaEntityFactory.userJpaEntity();

            insertKeycloakProfile(userJpaEntity);

            final UserJpaEntity newUserJpaEntity = userJpaRepository.save(userJpaEntity);
            userJpaEntities.add(newUserJpaEntity);
        }
        log.info("User Integration Testing Data created");
    }

    private void insertKeycloakProfile(final UserJpaEntity userJpaEntity) {

        final UserRepresentation userRepresentation = UserMockData
                .createUserRepresentation(
                        userJpaEntity.getEmail(),
                        userJpaEntity.getFirstName(),
                        userJpaEntity.getLastName(),
                        testPassword
                );

        final Keycloak keycloak = keycloakConfig.getKeycloak();

        final UsersResource usersResource = keycloak.realm(keycloakConfig.getRealm())
                                                    .users();
        try (final Response res = usersResource.create(userRepresentation);) {
            log.info(res.toString());
        }
    }
}
