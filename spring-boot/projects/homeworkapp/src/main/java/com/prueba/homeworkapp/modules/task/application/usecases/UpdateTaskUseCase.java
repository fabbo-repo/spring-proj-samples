package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.modules.task.domain.dtos.UpdateTaskDto;

public interface UpdateTaskUseCase {
    void updateTask(final UpdateTaskDto task);
}
