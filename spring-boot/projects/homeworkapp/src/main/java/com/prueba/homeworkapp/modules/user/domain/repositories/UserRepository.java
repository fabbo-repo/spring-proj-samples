package com.prueba.homeworkapp.modules.user.domain.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    boolean existsById(final UUID id);

    Optional<UserJpaEntity> findById(final UUID id);

    PageDto<User> findAll(final int pageNum);

    void deleteById(final UUID id);

    UserJpaEntity save(final UserJpaEntity userJpaEntity);
}
