package com.prueba.homeworkapp.modules.user.application.rest_controllers;

import com.prueba.homeworkapp.modules.user.domain.models.requests.CredentialsRequest;
import com.prueba.homeworkapp.modules.user.domain.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.domain.models.responses.ProfileResponse;
import com.prueba.homeworkapp.modules.user.domain.models.responses.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public ResponseEntity<UserResponse> getUser() {
        // TODO
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(final UUID id) {
        // TODO
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody @Valid UserRequest user
    ) {
        // TODO
    }

    @PostMapping("/access")
    public ResponseEntity<JwtsResponse> getAccessToken(
            @RequestBody @Valid CredentialsRequest credentialsRequest
    ) {
        // TODO
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtsResponse> refreshToken(
            @RequestBody @Valid CredentialsRequest credentialsRequest
    ) {
        // TODO
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UserRequest userRequest
    ) {
        // TODO
    }

    @GetMapping
    public ResponseEntity<Void> deleteUser() {
        // TODO
    }
}
