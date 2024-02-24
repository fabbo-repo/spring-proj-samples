package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.commons.exceptions.EntityNotFoundException;
import com.prueba.homeworkapp.commons.models.ApiPage;
import com.prueba.homeworkapp.modules.task.application.repositories.TaskRepository;
import com.prueba.homeworkapp.modules.task.application.usecases.CreateTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.DeleteTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.FilterTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTaskUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.GetTasksUseCase;
import com.prueba.homeworkapp.modules.task.application.usecases.UpdateTaskUseCase;
import com.prueba.homeworkapp.modules.task.domain.dtos.UpdateTaskDto;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.exceptions.TaskAlreadyFinishedException;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
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
                        () -> new EntityNotFoundException(
                                TaskJpaEntity.TABLE_NAME,
                                TaskJpaEntity.ID_COL,
                                taskId
                        )
                );
    }

    @Override
    public ApiPage<Task> getTasks(final int pageNum) {
        return taskRepository
                .findAll(pageNum);
    }

    @Override
    public ApiPage<Task> filterTasks(
            final TaskStatusEnum status,
            final int pageNum
    ) {
        return taskRepository
                .findAllByTaskStatus(status, pageNum);
    }

    @Override
    public Task createTask(final Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void updateTask(final UpdateTaskDto updateTaskDto) {
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
            throw new EntityNotFoundException(
                    TaskJpaEntity.TABLE_NAME,
                    TaskJpaEntity.ID_COL,
                    taskId
            );
        }
        taskRepository.deleteById(taskId);
    }
}
