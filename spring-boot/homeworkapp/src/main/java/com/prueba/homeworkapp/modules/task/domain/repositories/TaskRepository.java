package com.prueba.homeworkapp.modules.task.domain.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;

import java.util.UUID;

public interface TaskRepository {
    boolean existsById(final UUID id);

    Task findById(final UUID id);

    boolean isFinishedById(UUID id);

    PageDto<Task> findAll(final int pageNum);

    PageDto<Task> findAllByTaskStatus(final TaskStatusEnum taskStatus, final int pageNum);

    void deleteById(final UUID id);

    Task save(final Task task);

    void saveTaskAsFinished(final UUID id);
}
