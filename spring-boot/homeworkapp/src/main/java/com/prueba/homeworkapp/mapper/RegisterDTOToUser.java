package com.prueba.homeworkapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prueba.homeworkapp.persistence.entity.Role;
import com.prueba.homeworkapp.persistence.entity.User;
import com.prueba.homeworkapp.persistence.repository.RoleRepo;
import com.prueba.homeworkapp.service.dto.RegisterDTO;

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
