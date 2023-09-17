package com.prueba.homeworkapp.modules.auth.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Jwts {
    private String accessToken;

    private String refreshToken;

    private long accessExpiresIn;

    private long refreshExpiresIn;
}
