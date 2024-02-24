package com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RefreshRequest(
        @NotNull
        String refreshToken
) {
}
