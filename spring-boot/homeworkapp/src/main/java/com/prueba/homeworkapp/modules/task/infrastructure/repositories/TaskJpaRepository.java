package com.prueba.homeworkapp.modules.task.infrastructure.repositories;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.infrastructure.models.entities.TaskJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID> {
    Page<TaskJpaEntity> findAllByTaskStatus(final TaskStatusEnum taskStatus, final Pageable pageable);

    @Query(
            """
                    UPDATE Task t
                    SET t.finished = true
                    WHERE t.id = :id
                    """
    )
    @Modifying
    void saveTaskAsFinished(final UUID id);

    <T> Optional<T> findById(final UUID id, Class<T> projection);
}
