package com.prueba.homeworkapp.modules.task.infrastructure.repositories.jpa;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<Task, UUID> {
    Page<Task> findAllByTaskStatus(final TaskStatusEnum taskStatus, final Pageable pageable);

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
