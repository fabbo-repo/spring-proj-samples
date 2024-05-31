package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.common.data.exceptions.ApiResourceNotFoundException;
import com.prueba.homeworkapp.common.data.models.ApiPage;
import com.prueba.homeworkapp.common.data.props.ApiPageProps;
import com.prueba.homeworkapp.modules.task.application.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.application.usecases.CreateTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.DeleteTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.FilterTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.UpdateTaskUseCase;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.exceptions.TaskAlreadyFinishedException;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.props.CreateTaskProps;
import com.prueba.homeworkapp.modules.task.domain.props.FilerTasksProps;
import com.prueba.homeworkapp.modules.task.domain.props.UpdateTaskProps;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService
        implements GetTasksUseCase,
                   GetTaskUseCase,
                   FilterTasksUseCase,
                   CreateTaskUseCase,
                   UpdateTaskUseCase,
                   DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    @Override
    public Task getTask(final UUID taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(
                        () -> new ApiResourceNotFoundException(
                                TaskJpaEntity.TABLE_NAME,
                                TaskJpaEntity.ID_COL,
                                taskId
                        )
                );
    }

    @Override
    public ApiPage<Task> getTasks(final ApiPageProps apiPageProps) {
        return taskRepository
                .findAll(
                        apiPageProps.getPageNum(),
                        apiPageProps.getPageSize()
                );
    }

    @Override
    public ApiPage<Task> filterTasks(final FilerTasksProps filerTasksProps) {
        return taskRepository.findAllByTaskStatus(
                filerTasksProps.getTaskStatus(),
                filerTasksProps.getPageNum(),
                filerTasksProps.getPageSize()
        );
    }

    @Override
    public Task createTask(final CreateTaskProps createTaskProps) {
        final Task newTask = new Task(
                null,
                createTaskProps.getTitle(),
                createTaskProps.getDescription(),
                createTaskProps.getEstimatedDoneAt()
        );
        newTask.setFinishedAt(createTaskProps.getFinishedAt());
        newTask.setFinished(createTaskProps.isFinished());

        return taskRepository.save(newTask);
    }

    @Override
    public void updateTask(final UpdateTaskProps updateTaskDto) {
        final Task storedTask = getTask(updateTaskDto.getId());
        if (storedTask.isFinished()) {
            throw new TaskAlreadyFinishedException(
                    storedTask.getId()
            );
        }
        storedTask.setFinished(updateTaskDto.isFinished());
        taskRepository.save(storedTask);
    }

    @Override
    public void deleteTask(final UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ApiResourceNotFoundException(
                    TaskJpaEntity.TABLE_NAME,
                    TaskJpaEntity.ID_COL,
                    taskId
            );
        }
        taskRepository.deleteById(taskId);
    }
}
