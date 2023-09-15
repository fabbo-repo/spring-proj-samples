package com.prueba.homeworkapp.modules.user.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserPatchRequest(
        @NotEmpty
        @Size(max = 30)
        String username,
        @Size(max = 50)
        String firstName,
        @Size(max = 50)
        String lastName,
        @Min(0)
        @Min(200)
        int age
) {
}
