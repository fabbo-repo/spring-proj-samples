package com.prueba.homeworkapp.modules.task.application.repositories;

import com.prueba.homeworkapp.common.data.models.ApiPage;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    boolean existsById(final UUID id);

    Optional<Task> findById(final UUID id);

    ApiPage<Task> findAll(int pageNum, int pageSize);

    ApiPage<Task> findAllByTaskStatus(
            final TaskStatusEnum taskStatus,
            final int pageNum,
            final int pageSize
    );

    void deleteById(final UUID id);

    Task save(final Task task);

    ApiPage<Task> findAll(
            String title,
            String description,
            Boolean finished,
            LocalDateTime futureEstimatedDoneAt,
            int pageNum,
            int pageSize
    );
}
