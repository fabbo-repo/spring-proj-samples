package com.prueba.homeworkapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.prueba.homeworkapp.persistence.entity.Role;
import com.prueba.homeworkapp.persistence.entity.User;
import com.prueba.homeworkapp.persistence.repository.RoleRepo;
import com.prueba.homeworkapp.persistence.repository.UserRepo;
import com.prueba.homeworkapp.service.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {
	
	/**
	 * userRepo is injected via args constructor created by lombok.
	 * Check RequiredArgsConstructor annotation.
	 */
	private final UserRepo userRepo;

	/**
	 * roleRepo is injected via args constructor created by lombok.
	 * Check RequiredArgsConstructor annotation.
	 */
	private final RoleRepo roleRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final com.prueba.homeworkapp.mapper.RegisterDTOToUser mapper;

	@Override
	public User saveUser(User user) {
		log.info("Saving new user {} to the database", user.getUsername());
		// Password is hashed before it is saved in database
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to user {}", username, roleName);
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		// As the Transactional annotation is used, the role will be saved
		// automatically with this instruction
		if (!user.getRoles().contains(role)) user.getRoles().add(role);
		else log.warn("Trying to add awarded role {} to user {}", username, roleName);
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			log.info("User found in the database: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		// The full package name is used to diferentiate with current project User
		return new org.springframework.security.core.userdetails.User(
			user.getUsername(), user.getPassword(), authorities);
	}
	
	@Override
	public boolean existsByUsername(String username) {
		try {
			loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			return false;
		}
		return true;
	}

	@Override
	public User createUser(RegisterDTO registerDTO) {
		User user = mapper.map(registerDTO);
		return this.userRepo.save(user);
	}
}
