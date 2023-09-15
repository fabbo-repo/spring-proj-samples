package com.prueba.homeworkapp.modules.auth.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Jwts {
    private String accessToken;

    private String refreshToken;
}
