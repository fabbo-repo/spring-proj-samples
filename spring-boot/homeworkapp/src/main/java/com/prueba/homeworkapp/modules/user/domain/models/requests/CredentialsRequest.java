package com.prueba.homeworkapp.modules.user.domain.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CredentialsRequest(
		@NotNull
		@Email
		String email,
		@NotBlank
		@Size(max = 300)
		String password
) {
}
