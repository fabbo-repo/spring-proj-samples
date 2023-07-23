package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.core.domain.responses.PageResponse;
import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;

import java.util.UUID;

public interface TaskService {

    TaskResponse createTask(final TaskRequest request);

    TaskResponse getTask(final UUID id);

    PageResponse<TaskResponse> getTasks(final int pageNum);

    PageResponse<TaskResponse> getTasksByTaskStatus(final TaskStatusEnum taskStatus, final int pageNum);

    void deleteTask(final UUID id);

    void updateTaskAsFinished(final UUID id);
}