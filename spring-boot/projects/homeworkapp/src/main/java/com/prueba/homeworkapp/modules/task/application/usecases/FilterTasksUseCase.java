package com.prueba.homeworkapp.modules.task.application.usecases;

import com.prueba.homeworkapp.common.models.ApiPage;
import com.prueba.homeworkapp.modules.task.domain.enums.TaskStatusEnum;
import com.prueba.homeworkapp.modules.task.domain.models.Task;

public interface FilterTasksUseCase {
    ApiPage<Task> filterTasks(
            final TaskStatusEnum taskStatus,
            final int pageNum
    );
}
