package com.prueba.homeworkapp.modules.user.infrastructure.output.seeders;

import com.prueba.homeworkapp.commons.clients.AuthAdminClient;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.infrastructure.output.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserMockSeeder {

    private final UserJpaRepository userJpaRepository;

    private final AuthAdminClient authAdminClient;

    public void createData() {
        log.info("Creating User Mock Data...");

        final int minSize = Math.min(
                UserMockData.authData.size(),
                UserMockData.dbData.size()
        );

        for (int i = 0; i < minSize; i++) {
            final UUID userId = authAdminClient.createUser(
                    UserMockData
                            .authData
                            .get(i)
            );

            if (!userJpaRepository.existsById(userId)) {
                final UserJpaEntity userJpaEntity = UserMockData
                        .dbData
                        .get(i);
                userJpaRepository.save(
                        UserJpaEntity
                                .builder()
                                .id(userJpaEntity.getId())
                                .email(userJpaEntity.getEmail())
                                .firstName(userJpaEntity.getFirstName())
                                .lastName(userJpaEntity.getLastName())
                                .age(userJpaEntity.getAge())
                                .build()
                );
            }
        }
        log.info("User Mock Data created");
    }

    public void deleteData() {
        log.info("Removing User Mock Data...");
        for (final UserJpaEntity userJpaEntity : UserMockData.dbData) {
            userJpaRepository.deleteById(
                    userJpaEntity.getId()
            );
        }
        log.info("User Mock Data removed");
    }
}
