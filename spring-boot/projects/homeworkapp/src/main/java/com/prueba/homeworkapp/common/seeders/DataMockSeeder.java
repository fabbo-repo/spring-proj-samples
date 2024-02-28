package com.prueba.homeworkapp.common.seeders;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.modules.user.infrastructure.output.seeders.UserMockSeeder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile(HomeworkappApplication.DEV_PROFILE)
@Slf4j
public class DataMockSeeder {
    private final UserMockSeeder userMockSeeder;

    @PostConstruct
    private void onStartup() {
        log.info("Initializing Mock Data Seeder...");
        userMockSeeder.createData();
        log.info("Mock Data Seeder initialized");
    }

    @PreDestroy
    private void onShutdown() {
        log.info("Turning off Mock Data Seeder...");
        userMockSeeder.deleteData();
        log.info("Mock Data Seeder turned off");
    }
}
