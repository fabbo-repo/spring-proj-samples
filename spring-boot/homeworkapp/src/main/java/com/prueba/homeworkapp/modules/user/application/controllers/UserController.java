package com.prueba.homeworkapp.modules.user.application.controllers;

import com.prueba.homeworkapp.modules.user.application.models.mappers.UserControllerMapper;
import com.prueba.homeworkapp.modules.user.application.models.requests.UserPatchRequest;
import com.prueba.homeworkapp.modules.user.application.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.application.models.responses.ProfileResponse;
import com.prueba.homeworkapp.modules.user.application.models.responses.UserResponse;
import com.prueba.homeworkapp.modules.user.domain.models.dtos.User;
import com.prueba.homeworkapp.modules.user.domain.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserControllerMapper userMapper = UserControllerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<UserResponse> getUser() {
        final User user = userService.getUser(UUID.randomUUID());
        return ResponseEntity.ok(userMapper.dtoToResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(final UUID id) {
        final User user = userService.getUser(id);
        return ResponseEntity.ok(userMapper.dtoToProfileResponse(user));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UserRequest userRequest
    ) {
        final User user = userMapper.requestToDto(userRequest);
        userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> patchUser(
            @RequestBody @Valid UserPatchRequest userRequest
    ) {
        final User user = userMapper.patchRequestToDto(userRequest);
        userService.patchUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser(UUID.randomUUID());
        return ResponseEntity.noContent().build();
    }
}
