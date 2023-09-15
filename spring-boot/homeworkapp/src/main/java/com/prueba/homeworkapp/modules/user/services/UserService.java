package com.prueba.homeworkapp.modules.user.services;

import com.prueba.homeworkapp.modules.user.models.dtos.User;

import java.util.UUID;

public interface UserService {
    User getUser(final UUID id);

    void updateUser(final User user);

    void patchUser(final User user);

    void deleteUser(final UUID id);
}
