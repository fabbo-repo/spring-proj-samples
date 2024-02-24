package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.modules.task.domain.models.Task;

import java.util.UUID;

public interface GetTaskUseCase {
    Task getTask(final UUID taskId);
}