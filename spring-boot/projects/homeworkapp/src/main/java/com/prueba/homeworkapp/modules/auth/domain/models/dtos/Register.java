package com.prueba.homeworkapp.modules.auth.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Register {
    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;
}
