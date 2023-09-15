package com.prueba.homeworkapp.modules.auth.models.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Refresh {
    private String refreshToken;
}
