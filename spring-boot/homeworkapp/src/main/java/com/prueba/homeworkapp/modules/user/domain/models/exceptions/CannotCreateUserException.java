package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

public class CannotCreateUserException extends RuntimeException {
    public CannotCreateUserException(final String email) {
        super(String.format("Cannot create user with email %s", email));
    }
}