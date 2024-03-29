package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String email) {
        super(String.format("User with email %s already exists", email));
    }
}