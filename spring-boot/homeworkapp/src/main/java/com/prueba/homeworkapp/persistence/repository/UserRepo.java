package com.prueba.homeworkapp.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.homeworkapp.persistence.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{
	/**
	 * This method is interpreted by Spring as SELECT statement.
	 * For that it should be name as the field and should have the field as a parameter
	 */
	User findByUsername(String username);
}
