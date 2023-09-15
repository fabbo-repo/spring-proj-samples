package com.prueba.homeworkapp.modules.user.repositories.jpa;

import com.prueba.homeworkapp.modules.user.models.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
}
