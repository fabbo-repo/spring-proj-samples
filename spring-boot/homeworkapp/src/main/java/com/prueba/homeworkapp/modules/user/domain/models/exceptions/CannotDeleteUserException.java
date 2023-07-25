package com.prueba.homeworkapp.modules.user.domain.models.exceptions;

import java.util.UUID;

public class CannotDeleteUserException extends RuntimeException {
    public CannotDeleteUserException(final UUID id) {
        super(String.format("Cannot delete user with email %s", id));
    }
}