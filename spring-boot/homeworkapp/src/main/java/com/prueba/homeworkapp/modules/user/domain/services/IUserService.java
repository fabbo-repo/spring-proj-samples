package com.prueba.homeworkapp.modules.user.domain.services;

import java.util.List;
import com.prueba.homeworkapp.modules.user.domain.models.entities.Role;
import com.prueba.homeworkapp.modules.user.domain.models.entities.User;
import com.prueba.homeworkapp.auth.domain.models.dtos.RegisterDTO;

public interface IUserService {
	
	User saveUser(User user);
	
	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);
	
	User getUser(String username);
	
	List<User> getUsers();
	
	boolean existsByUsername(String username);

	User createUser(RegisterDTO registerDTO);
}
