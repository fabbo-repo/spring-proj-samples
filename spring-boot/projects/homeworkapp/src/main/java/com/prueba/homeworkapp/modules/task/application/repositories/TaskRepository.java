package com.prueba.homeworkapp.modules.task.application.repositories;

import com.prueba.homeworkapp.common.models.ApiPage;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    boolean existsById(final UUID id);

    Optional<Task> findById(final UUID id);

    ApiPage<Task> findAll(final int pageNum);

    ApiPage<Task> findAllByTaskStatus(
            final TaskStatusEnum taskStatus,
            final int pageNum
    );

    void deleteById(final UUID id);

    Task save(final Task task);

    ApiPage<Task> findAll(
            String title,
            String description,
            Boolean finished,
            LocalDateTime futureEstimatedDoneAt,
            int pageNum
    );
}
