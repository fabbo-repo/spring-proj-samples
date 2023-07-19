package com.prueba.homeworkapp.auth.domain.models.dtos;

import lombok.Data;

@Data
public class RegisterDTO {
    
	private String name;
    
    private String username;
    
    private String password;
}
