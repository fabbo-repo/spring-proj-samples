package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.modules.task.application.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.props.CreateTaskProps;
import com.prueba.homeworkapp.modules.task.domain.props.CreateTaskPropsFactory;
import com.prueba.homeworkapp.modules.task.domain.sevices.TaskService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.prueba.homeworkapp.common.utils.TestDataUtils.randomUuid;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@RunWith(MockitoJUnitRunner.class)
class CreateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void givenValidUseCaseRequest_whenCreateTask_shouldSaveNewTask() {
        final CreateTaskProps createTaskProps = CreateTaskPropsFactory
                .createTaskProps();

        final Task toSaveTask = new Task(
                null,
                createTaskProps.getTitle(),
                createTaskProps.getDescription(),
                createTaskProps.getEstimatedDoneAt()
        );
        toSaveTask.setFinishedAt(createTaskProps.getFinishedAt());
        toSaveTask.setFinished(createTaskProps.isFinished());

        final Task expectedTask = new Task(
                randomUuid(),
                createTaskProps.getTitle(),
                createTaskProps.getDescription(),
                createTaskProps.getEstimatedDoneAt()
        );
        expectedTask.setFinishedAt(createTaskProps.getFinishedAt());
        expectedTask.setFinished(createTaskProps.isFinished());

        when(taskRepository.save(toSaveTask))
                .thenReturn(expectedTask);

        final Task result = taskService.createTask(createTaskProps);

        Assertions.assertThat(result)
                  .usingRecursiveComparison()
                  .isEqualTo(expectedTask);
    }
}