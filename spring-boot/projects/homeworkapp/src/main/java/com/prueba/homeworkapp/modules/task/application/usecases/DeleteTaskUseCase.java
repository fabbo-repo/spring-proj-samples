package com.prueba.homeworkapp.modules.task.application.usecases;

import java.util.UUID;

public interface DeleteTaskUseCase {
    void deleteTask(final UUID id);
}
