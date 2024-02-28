package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

import com.prueba.homeworkapp.common.exceptions.ApiException;

import java.util.UUID;

public class CannotDeleteUserException extends ApiException {
    public CannotDeleteUserException(final UUID id) {
        super(String.format("Cannot delete user with email %s", id));
    }
}