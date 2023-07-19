package com.prueba.homeworkapp.modules.user.infrastructure.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.homeworkapp.modules.user.domain.models.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	/**
	 * This method is interpreted by Spring as SELECT statement
	 * For that it should be name as the field and should have the field as a parameter
	 */
	Role findByName(String name);
}
