package com.prueba.homeworkapp.modules.task.domain.repositories;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.views.TaskFinishedView;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    boolean existsById(final UUID id);

    Optional<Task> findById(final UUID id);

    Optional<TaskFinishedView> findFinishedViewById(UUID id);

    Page<Task> findAll(final int pageNum);

    Page<Task> findAllByTaskStatus(final TaskStatusEnum taskStatus, final int pageNum);

    void deleteById(final UUID id);

    Task save(final Task task);

    void saveTaskAsFinished(final UUID id);
}
