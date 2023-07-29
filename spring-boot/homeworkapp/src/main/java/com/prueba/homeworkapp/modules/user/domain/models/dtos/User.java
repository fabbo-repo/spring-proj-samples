package com.prueba.homeworkapp.modules.user.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
    private UUID id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Integer age;
}
