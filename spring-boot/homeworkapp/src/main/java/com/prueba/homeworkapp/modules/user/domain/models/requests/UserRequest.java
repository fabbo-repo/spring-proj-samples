package com.prueba.homeworkapp.modules.user.domain.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRequest(
        @NotBlank
        @Size(max = 30)
        String username,
        @NotNull
        @Email
        String email,
        @NotBlank
        @Size(max = 300)
        String password,
        @NotBlank
        @Size(max = 50)
        String firstName,
        @NotBlank
        @Size(max = 50)
        String lastName,
        @Min(0)
        @Min(200)
        int age
) {
}
