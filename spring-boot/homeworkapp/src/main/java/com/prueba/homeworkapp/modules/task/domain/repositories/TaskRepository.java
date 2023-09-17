package com.prueba.homeworkapp.modules.task.domain.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    boolean existsById(final UUID id);

    Optional<TaskJpaEntity> findById(final UUID id);

    boolean isFinishedById(UUID id);

    PageDto<TaskJpaEntity> findAll(final int pageNum);

    PageDto<TaskJpaEntity> findAllByTaskStatus(final TaskStatusEnum taskStatus, final int pageNum);

    void deleteById(final UUID id);

    TaskJpaEntity save(final TaskJpaEntity task);

    void saveTaskAsFinished(final UUID id);

    PageDto<TaskJpaEntity> findAll(
            String title,
            String description,
            Boolean finished,
            LocalDateTime futureEstimatedDoneAt,
            int pageNum
    );
}
