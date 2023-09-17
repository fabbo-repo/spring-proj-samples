package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.task.domain.models.dtos.Task;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;

import java.util.UUID;

public interface TaskService {

    Task createTask(final Task task);

    Task getTask(final UUID id);

    PageDto<Task> getTasks(final int pageNum);

    PageDto<Task> getTasksByTaskStatus(final TaskStatusEnum taskStatus, final int pageNum);

    void deleteTask(final UUID id);

    void updateTaskAsFinished(final UUID id);
}