package com.prueba.homeworkapp.modules.user.domain.services;

import com.prueba.homeworkapp.modules.auth.infrastructure.clients.keycloak.KeycloakAdminClient;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final KeycloakAdminClient keycloakAdminClient;

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(final User user) {
        final User dbUser = getUser(user.getId());
        dbUser.setUsername(user.getUsername());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setAge(user.getAge());
        userRepository.save(dbUser);
    }

    @Override
    public void patchUser(User user) {

    }

    @Override
    public void deleteUser(UUID id) {

    }
}
