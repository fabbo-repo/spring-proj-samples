package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;

import java.util.UUID;

public interface TaskService {

    TaskResponse createTask(final TaskRequest task);

    TaskResponse getTask(final UUID id);

    PageDto<TaskResponse> getTasks(final int pageNum);

    PageDto<TaskResponse> getTasksByTaskStatus(final TaskStatusEnum taskStatus, final int pageNum);

    void deleteTask(final UUID id);

    void updateTaskAsFinished(final UUID id);
}