package com.prueba.homeworkapp.commons.seeders;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.modules.task.infrastructure.output.seeders.TaskIntegrationTestingSeeder;
import com.prueba.homeworkapp.modules.user.infrastructure.output.seeders.UserIntegrationTestingSeeder;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(HomeworkappApplication.INT_TEST_PROFILE)
@Component
@RequiredArgsConstructor
@Slf4j
@Getter
public class IntegrationTestingSeeder {
    private final UserIntegrationTestingSeeder userIntegrationTestingSeeder;

    private final TaskIntegrationTestingSeeder taskIntegrationTestingSeeder;

    @PostConstruct
    private void onStartup() {
        log.info("Initializing Integration Testing Data Seeder...");

        userIntegrationTestingSeeder.insertData();
        taskIntegrationTestingSeeder.insertData();

        log.info("Integration Testing Data Seeder initialized");
    }
}
