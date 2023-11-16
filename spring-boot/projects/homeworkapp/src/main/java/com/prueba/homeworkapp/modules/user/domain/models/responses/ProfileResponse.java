package com.prueba.homeworkapp.modules.user.domain.models.responses;

import java.util.UUID;

public record ProfileResponse(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
