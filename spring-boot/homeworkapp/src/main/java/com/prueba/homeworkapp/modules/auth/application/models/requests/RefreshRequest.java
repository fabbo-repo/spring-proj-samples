package com.prueba.homeworkapp.modules.auth.application.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RefreshRequest(
        @NotNull
        String refreshToken
) {
}
