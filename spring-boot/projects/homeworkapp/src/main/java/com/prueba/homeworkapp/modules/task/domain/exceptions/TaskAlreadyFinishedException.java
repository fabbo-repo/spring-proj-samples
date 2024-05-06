package com.prueba.homeworkapp.modules.task.domain.exceptions;

import com.prueba.homeworkapp.common.data.exceptions.ApiCodeException;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;

public class TaskAlreadyFinishedException extends ApiCodeException {
    public TaskAlreadyFinishedException(final Object id) {
        super(100, String.format("Already finished task with %s: %s", TaskJpaEntity.ID_COL, id));
    }
}