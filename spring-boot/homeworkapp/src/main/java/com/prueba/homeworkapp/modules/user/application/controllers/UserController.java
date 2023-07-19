package com.prueba.homeworkapp.modules.user.application.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prueba.homeworkapp.core.config.SecurityConfig;
import com.prueba.homeworkapp.modules.user.domain.models.entities.Role;
import com.prueba.homeworkapp.modules.user.domain.models.entities.User;
import com.prueba.homeworkapp.modules.user.domain.services.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY_CONFIG_NAME)
public class UserController {
	private final IUserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		// Return 200 response and a list of users
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/user/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		// Get this method url
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		// Return 201 response
		return ResponseEntity.created(uri).body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		// Get this method url
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		// Return 201 response
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}
	

	@PostMapping("/role/add-to-user")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getUsername(), form.getRoleName());
		// Return 200 response without data
		return ResponseEntity.ok().build();
	}
	
	@Data
	class RoleToUserForm {
		private String username;
		private String roleName;
	}
}
