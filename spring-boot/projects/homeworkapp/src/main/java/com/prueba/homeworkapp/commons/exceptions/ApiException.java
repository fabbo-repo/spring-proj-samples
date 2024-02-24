package com.prueba.homeworkapp.commons.exceptions;

public class ApiException extends RuntimeException {
    public ApiException(final String message) {
        super(message);
    }
}