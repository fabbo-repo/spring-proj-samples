package com.prueba.homeworkapp.modules.task.infrastructure.repositories.jpa;

import com.prueba.homeworkapp.modules.task.domain.models.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID> {
    Page<TaskJpaEntity> findAllByTaskStatus(final TaskStatusEnum taskStatus, final Pageable pageable);

    <T> Optional<T> findById(final UUID id, Class<T> projection);
}
