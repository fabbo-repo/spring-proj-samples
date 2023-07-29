package com.prueba.homeworkapp.modules.user.application.models.responses;

import java.util.UUID;

/**
 * Own account data response
 */
public record UserResponse(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName,
        Integer age
) {
}
