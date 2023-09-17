package com.prueba.homeworkapp.core.seeders;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.modules.user.infrastructure.seeders.UserJpaMockSeeder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile(HomeworkappApplication.DEV_PROFILE)
@Slf4j
public class JpaMockSeeder {
    private final UserJpaMockSeeder userJpaMockSeeder;

    @PostConstruct
    private void onStartup() {
        log.info("Initializing Jpa Mock Data Seeder...");
        userJpaMockSeeder.createData();
        log.info("Jpa Mock Data Seeder initialized");
    }

    @PreDestroy
    private void onShutdown() {
        log.info("Turning off Jpa Mock Data Seeder...");
        userJpaMockSeeder.deleteData();
        log.info("Jpa Mock Data Seeder turned off");
    }
}
