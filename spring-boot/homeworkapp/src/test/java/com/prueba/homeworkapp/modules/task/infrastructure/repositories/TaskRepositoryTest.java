package com.prueba.homeworkapp.modules.task.infrastructure.repositories;


import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.annotations.EnabledIfDocker;
import com.prueba.homeworkapp.containers.MockPostgreSqlContainer;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntityFactory;
import com.prueba.homeworkapp.modules.task.domain.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static com.prueba.homeworkapp.TestUtils.randomInt;
import static com.prueba.homeworkapp.TestUtils.randomUuid;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(HomeworkappApplication.INT_TEST_PROFILE)
class TaskRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSqlContainer = MockPostgreSqlContainer.getInstance();

    @Autowired
    private TaskRepository taskRepository;

    private TaskJpaEntity mockedTask;
    private final List<TaskJpaEntity> mockedTaskList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockedTask = taskRepository.save(TaskJpaEntityFactory.taskJpaEntity());
        mockedTaskList.add(mockedTask);
        for (int i = 0; i < randomInt(20, 40); i++) {
            mockedTaskList.add(
                    taskRepository.save(
                            TaskJpaEntityFactory
                                    .taskJpaEntity()
                    )
            );
        }
    }

    @Test
    @Transactional
    void storedTaskJpaEntity_whenExistsById_shouldReturnTrue() {
        final boolean taskExists = taskRepository.existsById(
                mockedTask.getId()
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
