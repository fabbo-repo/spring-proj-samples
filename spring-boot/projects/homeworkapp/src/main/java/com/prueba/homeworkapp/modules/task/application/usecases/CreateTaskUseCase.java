package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.modules.task.domain.models.Task;

public interface CreateTaskUseCase {

    Task createTask(final Task task);
}