package com.prueba.homeworkapp.commons.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(final String entityName, final String idName, final Object id) {
        super(String.format("Could not find %s with %s: %s", entityName, idName, id));
    }
}