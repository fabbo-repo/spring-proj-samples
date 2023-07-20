package com.prueba.homeworkapp.modules.task.domain.repositories;

import com.prueba.homeworkapp.modules.task.domain.models.entities.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;

import java.util.List;

public interface TaskRepository {

    List<Task> findAllByTaskStatus(final TaskStatusEnum taskStatusEnum);

    Task save(final Task task);
}
