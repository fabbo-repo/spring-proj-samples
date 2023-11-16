package com.mercadonatest.eanapi.modules.auth.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokensDto {
    private String accessToken;

    private String refreshToken;
}
