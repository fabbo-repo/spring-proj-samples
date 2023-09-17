package com.prueba.homeworkapp.modules.user.infrastructure.repositories.jpa;

import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
}
