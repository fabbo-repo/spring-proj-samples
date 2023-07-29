package com.prueba.homeworkapp.modules.user.infrastructure.repositories;

import com.prueba.homeworkapp.modules.user.infrastructure.models.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
}
