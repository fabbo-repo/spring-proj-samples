package com.prueba.homeworkapp.modules.auth.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Refresh {
    private String refreshToken;
}
