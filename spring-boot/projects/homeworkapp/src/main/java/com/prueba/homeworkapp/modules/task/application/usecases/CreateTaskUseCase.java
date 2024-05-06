package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.props.CreateTaskProps;

public interface CreateTaskUseCase {

    Task createTask(final CreateTaskProps createTaskProps);
}