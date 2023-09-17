package com.prueba.homeworkapp.modules.user.infrastructure.repositories;

import com.prueba.homeworkapp.core.models.dtos.PageDto;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;

import java.util.UUID;
import java.util.function.Supplier;

public interface UserRepository {
    boolean existsById(final UUID id);

    User findById(final UUID id);

    /**
     * @param orElse: supplier to execute in case User does not exists
     */
    User findById(UUID id, Supplier<User> orElse);

    PageDto<User> findAll(final int pageNum);

    void deleteById(final UUID id);

    User save(final User user);
}
