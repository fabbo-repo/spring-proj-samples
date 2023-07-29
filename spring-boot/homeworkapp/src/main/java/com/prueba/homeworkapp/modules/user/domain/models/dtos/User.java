package com.prueba.homeworkapp.modules.user.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Integer age;
}
