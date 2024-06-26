package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.common.data.models.ApiPage;
import com.prueba.homeworkapp.common.data.props.ApiPageProps;
import com.prueba.homeworkapp.modules.task.domain.models.Task;

public interface GetTasksUseCase {
    ApiPage<Task> getTasks(final ApiPageProps apiPageProps);
}