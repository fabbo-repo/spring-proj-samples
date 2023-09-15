package com.prueba.homeworkapp.modules.user.models.requests;

import jakarta.validation.constraints.Max;
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
        @Size(max = 50)
        String firstName,
        @NotNull
        @Size(max = 50)
        String lastName,
        @Min(0)
        @Max(200)
        int age
) {
}
