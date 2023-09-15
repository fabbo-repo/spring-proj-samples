package com.prueba.homeworkapp.modules.user.models.responses;

import java.time.LocalDateTime;
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
        Integer age,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
