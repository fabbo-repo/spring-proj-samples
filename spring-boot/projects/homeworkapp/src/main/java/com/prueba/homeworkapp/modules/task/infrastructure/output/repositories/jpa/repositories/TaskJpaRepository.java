package com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.repositories;

import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID>, JpaSpecificationExecutor<TaskJpaEntity> {
    Page<TaskJpaEntity> findAllByTaskStatus(final TaskStatusEnum taskStatus, final Pageable pageable);

    <T> Optional<T> findById(final UUID id, Class<T> projection);
}
