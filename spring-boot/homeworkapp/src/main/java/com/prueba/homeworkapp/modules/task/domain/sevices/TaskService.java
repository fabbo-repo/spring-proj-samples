package com.prueba.homeworkapp.modules.task.domain.sevices;

import com.prueba.homeworkapp.modules.task.domain.models.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.requests.TaskRequest;
import com.prueba.homeworkapp.modules.task.domain.models.responses.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(final TaskRequest request);

    TaskResponse findById(final Long id);

    List<TaskResponse> findAll();

    List<TaskResponse> findAllByTaskStatus(final TaskStatusEnum status);

    void updateTaskAsFinished(final Long id);

    void deleteById(final Long id);
}