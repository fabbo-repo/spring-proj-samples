package com.prueba.homeworkapp.common.data.exceptions;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(final String message) {
        super(message);
    }
}