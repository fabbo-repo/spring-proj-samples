package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.common.data.models.ApiPage;
import com.prueba.homeworkapp.modules.task.domain.models.Task;
import com.prueba.homeworkapp.modules.task.domain.props.FilerTasksProps;

public interface FilterTasksUseCase {
    ApiPage<Task> filterTasks(final FilerTasksProps filerTasksProps);
}
