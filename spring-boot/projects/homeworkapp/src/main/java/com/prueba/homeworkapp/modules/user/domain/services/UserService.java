package com.prueba.homeworkapp.modules.user.domain.services;

import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface UserService {
    User getUser(final UUID id);

    void createUserIfNotExists(
            Jwt jwt
    );

    void updateUser(final User user);

    void patchUser(final User user);

    void deleteUser(final UUID id);
}
