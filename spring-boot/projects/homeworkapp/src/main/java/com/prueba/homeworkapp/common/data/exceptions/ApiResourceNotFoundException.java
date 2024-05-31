package com.prueba.homeworkapp.common.data.exceptions;

public class ApiResourceNotFoundException extends RuntimeException {
    public ApiResourceNotFoundException(final String entityName, final String idName, final Object id) {
        super(String.format("Could not find %s with %s: %s", entityName, idName, id));
    }
}