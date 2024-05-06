package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

import com.prueba.homeworkapp.common.data.exceptions.ApiCodeException;

import java.util.UUID;

public class CannotDeleteUserException extends ApiCodeException {
    public CannotDeleteUserException(final UUID id) {
        super(10, String.format("Cannot delete user with email %s", id));
    }
}