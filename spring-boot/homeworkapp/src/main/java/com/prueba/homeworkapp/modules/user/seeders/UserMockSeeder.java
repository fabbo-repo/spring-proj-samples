package com.prueba.homeworkapp.modules.user.seeders;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.modules.user.models.entities.UserJpaEntity;
import com.prueba.homeworkapp.modules.user.repositories.jpa.UserJpaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Profile(HomeworkappApplication.DEV_PROFILE)
@RequiredArgsConstructor
@Slf4j
public class UserMockSeeder {
    private final List<UUID> userIds = new ArrayList<>();

    private final UserJpaRepository userJpaRepository;

    @PostConstruct
    private void onStartup() {
        log.info("Creating User Mock Data...");
        for (final MockData userMockData : MockData.values()) {
            final UserJpaEntity userJpaEntity = userJpaRepository.save(
                    UserJpaEntity
                            .builder()
                            .id(UUID.randomUUID())
                            .username(userMockData.username)
                            .email(userMockData.email)
                            .firstName(userMockData.firstName)
                            .lastName(userMockData.lastName)
                            .age(userMockData.age)
                            .build()
            );
            userIds.add(userJpaEntity.getId());
        }
        log.info("User Mock Data created");
    }

    @PreDestroy
    private void onShutdown() {
        log.info("Removing User Mock Data...");
        for (final UUID userId : userIds) {
            userJpaRepository.deleteById(userId);
        }
        log.info("User Mock Data removed");
    }

    @RequiredArgsConstructor
    private enum MockData {
        USER_1(
                "addison42",
                "xfarrell@example.org",
                "Sonia",
                "Metz",
                56
        ),
        USER_2(
                "tom20",
                "harber.jordy@example.net",
                "Demario",
                "O'Reilly",
                18
        ),
        USER_3(
                "aveum",
                "bgerlach@example.net",
                "Florian",
                "Schmeler",
                34
        );

        private final String username;
        private final String email;
        private final String firstName;
        private final String lastName;
        private final Integer age;
    }
}
