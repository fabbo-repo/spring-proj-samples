package com.prueba.homeworkapp.modules.auth.domain.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAndJwts {
	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private Integer age;

	private String accessToken;

	private String refreshToken;
}
