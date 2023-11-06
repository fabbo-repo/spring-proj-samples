package com.prueba.homeworkapp.modules.task.infrastructure.repositories;


import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.annotations.EnabledIfDocker;
import com.prueba.homeworkapp.containers.IntegrationContainerTests;
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
class TaskRepositoryTest extends IntegrationContainerTests {

    @Autowired
    private TaskRepository taskRepository;

    private TaskJpaEntity testTask;

    private final List<TaskJpaEntity> testTaskList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        testTask = taskRepository.save(TaskJpaEntityFactory.taskJpaEntity());
        testTaskList.add(testTask);
        for (int i = 0; i < randomInt(20, 40); i++) {
            testTaskList.add(
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
                testTask.getId()
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
