package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.commons.models.ApiPage;
import com.prueba.homeworkapp.modules.task.domain.models.Task;

public interface GetTasksUseCase {
    ApiPage<Task> getTasks(final int pageNum);
}