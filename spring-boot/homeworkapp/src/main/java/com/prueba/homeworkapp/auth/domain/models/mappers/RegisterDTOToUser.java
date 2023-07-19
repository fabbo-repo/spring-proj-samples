package com.prueba.homeworkapp.auth.domain.models.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prueba.homeworkapp.modules.user.domain.models.entities.Role;
import com.prueba.homeworkapp.modules.user.domain.models.entities.User;
import com.prueba.homeworkapp.modules.user.infrastructure.jpa_repositories.RoleRepo;
import com.prueba.homeworkapp.auth.domain.models.dtos.RegisterDTO;

@Component
public class RegisterDTOToUser implements IMapper<RegisterDTO, User>{
	@Autowired
	RoleRepo roleRepo;
	
	@Override
	public User map(RegisterDTO in) {
		User user = new User();
		user.setName(in.getName());
		user.setUsername(in.getUsername());
		user.setPassword(in.getPassword());
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_USER"));
		user.setRoles(roles);
		return user;
	}
}
