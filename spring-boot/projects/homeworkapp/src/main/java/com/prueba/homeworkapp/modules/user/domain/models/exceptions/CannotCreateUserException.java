package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

import com.prueba.homeworkapp.common.data.exceptions.ApiCodeException;

public class CannotCreateUserException extends ApiCodeException {
    public CannotCreateUserException(final String email) {
        super(11, String.format("Cannot create user with email %s", email));
    }
}