package com.prueba.homeworkapp.modules.task.infrastructure.output.repositories;


import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.common.annotations.EnabledIfDocker;
import com.prueba.homeworkapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.common.containers.TestContainersIntegration;
import com.prueba.homeworkapp.common.seeders.IntegrationTestingSeeder;
import com.prueba.homeworkapp.modules.task.application.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.mappers.TaskJpaMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.prueba.homeworkapp.common.utils.TestDataUtils.randomUuid;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(HomeworkappApplication.INT_TEST_PROFILE)
class TaskRepositoryTest extends TestContainersIntegration {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private IntegrationTestingSeeder integrationTestingSeeder;

    private static final TaskJpaMapper TASK_JPA_MAPPER = TaskJpaMapper.INSTANCE;

    @Test
    @Transactional
    void storedTaskJpaEntity_whenExistsById_shouldReturnTrue() {
        final boolean taskExists = taskRepository.existsById(
                integrationTestingSeeder
                        .getTaskIntegrationTestingSeeder()
                        .getTaskJpaEntities()
                        .get(0)
                        .getId()
        );

        assertTrue(taskExists);
    }

    @Test
    @Transactional
    void nonexistentTaskJpaEntity_whenExistsById_shouldReturnFalse() {
        final boolean taskExists = taskRepository.existsById(
                randomUuid()
        );

        assertFalse(taskExists);
    }
}
