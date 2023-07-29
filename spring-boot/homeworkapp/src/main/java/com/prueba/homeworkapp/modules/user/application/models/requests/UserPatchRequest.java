package com.prueba.homeworkapp.modules.user.application.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserPatchRequest(
        @NotEmpty
        @Size(max = 30)
        String username,
        @Email
        String email,
        @NotEmpty
        @Size(max = 300)
        String password,
        @Size(max = 50)
        String firstName,
        @Size(max = 50)
        String lastName,
        @Min(0)
        @Min(200)
        int age
) {
}
