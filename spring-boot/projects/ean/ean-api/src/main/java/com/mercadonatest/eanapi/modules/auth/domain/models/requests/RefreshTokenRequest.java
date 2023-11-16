package com.mercadonatest.eanapi.modules.auth.domain.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequest(
        @NotBlank
        String refreshToken
) {
}
