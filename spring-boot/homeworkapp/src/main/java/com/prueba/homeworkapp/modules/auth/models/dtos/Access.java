package com.prueba.homeworkapp.modules.auth.models.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Access {
    private String email;

    private String password;
}
