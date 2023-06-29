package com.prueba.homeworkapp.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.homeworkapp.persistence.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	/**
	 * This method is interpreted by Spring as SELECT statement
	 * For that it should be name as the field and should have the field as a parameter
	 */
	Role findByName(String name);
}
