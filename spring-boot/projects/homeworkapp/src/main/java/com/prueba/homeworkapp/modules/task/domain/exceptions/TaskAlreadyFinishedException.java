package com.prueba.homeworkapp.modules.task.domain.exceptions;

import com.prueba.homeworkapp.common.exceptions.ApiException;
import com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;

public class TaskAlreadyFinishedException extends ApiException {
    public TaskAlreadyFinishedException(final Object id) {
        super(String.format("Already finished task with %s: %s", TaskJpaEntity.ID_COL, id));
    }
}