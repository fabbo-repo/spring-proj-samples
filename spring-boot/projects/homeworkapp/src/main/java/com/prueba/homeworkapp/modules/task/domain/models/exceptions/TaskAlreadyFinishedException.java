package com.prueba.homeworkapp.modules.task.domain.models.exceptions;

public class TaskAlreadyFinishedException extends RuntimeException {
    public TaskAlreadyFinishedException(final String idName, final Object id) {
        super(String.format("Already finished task with %s: %s", idName, id));
    }
}