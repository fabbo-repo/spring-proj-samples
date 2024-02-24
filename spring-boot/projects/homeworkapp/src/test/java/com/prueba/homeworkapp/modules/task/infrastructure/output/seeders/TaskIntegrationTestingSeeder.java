package com.prueba.homeworkapp.modules.task.infrastructure.output.seeders;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntityFactory;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.repositories.TaskJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Profile(HomeworkappApplication.INT_TEST_PROFILE)
@Component
@Slf4j
@Getter
public class TaskIntegrationTestingSeeder {

    private final List<TaskJpaEntity> taskJpaEntities = new ArrayList<>();

    private final TaskJpaRepository taskJpaRepository;

    public void insertData() {
        log.info("Creating Task Integration Testing Data...");
        for (int i = 0; i < 10; i++) {
            final TaskJpaEntity taskJpaEntity = TaskJpaEntityFactory.taskJpaEntity();

            final TaskJpaEntity newTaskJpaEntity = taskJpaRepository.save(taskJpaEntity);
            taskJpaEntities.add(newTaskJpaEntity);
        }
        log.info("Task Integration Testing Data created");
    }
}
