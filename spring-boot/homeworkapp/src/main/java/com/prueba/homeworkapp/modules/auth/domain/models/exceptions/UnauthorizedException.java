package com.prueba.homeworkapp.modules.auth.domain.models.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(final String message) {
        super(message);
    }
}