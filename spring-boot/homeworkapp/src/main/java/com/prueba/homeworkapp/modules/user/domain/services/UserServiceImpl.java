package com.prueba.homeworkapp.modules.user.domain.services;

import com.prueba.homeworkapp.modules.user.domain.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.domain.models.responses.UserResponse;
import com.prueba.homeworkapp.modules.user.infrastructure.clients.keycloak.KeycloakAdminClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl {
    private final UserRepository userRepository;

    private final KeycloakAdminClient keycloakAdminClient;

    public UserResponse createUser(final UserRequest request) {

    }
}
