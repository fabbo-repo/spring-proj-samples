package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

import com.prueba.homeworkapp.common.exceptions.ApiException;

public class CannotCreateUserException extends ApiException {
    public CannotCreateUserException(final String email) {
        super(String.format("Cannot create user with email %s", email));
    }
}