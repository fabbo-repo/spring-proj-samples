package com.prueba.homeworkapp.common.exceptions;

public class ApiException extends RuntimeException {
    public ApiException(final String message) {
        super(message);
    }
}