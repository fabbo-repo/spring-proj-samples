package com.prueba.homeworkapp.service;

import java.util.List;
import com.prueba.homeworkapp.persistence.entity.Role;
import com.prueba.homeworkapp.persistence.entity.User;
import com.prueba.homeworkapp.service.dto.RegisterDTO;

public interface IUserService {
	
	User saveUser(User user);
	
	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);
	
	User getUser(String username);
	
	List<User> getUsers();
	
	boolean existsByUsername(String username);

	User createUser(RegisterDTO registerDTO);
}
