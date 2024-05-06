package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.modules.task.domain.props.UpdateTaskProps;

public interface UpdateTaskUseCase {
    void updateTask(final UpdateTaskProps task);
}
