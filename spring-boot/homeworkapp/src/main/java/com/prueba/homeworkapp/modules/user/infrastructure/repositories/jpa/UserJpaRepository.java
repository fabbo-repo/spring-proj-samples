package com.prueba.homeworkapp.modules.user.infrastructure.repositories.jpa;

import com.prueba.homeworkapp.modules.user.domain.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
