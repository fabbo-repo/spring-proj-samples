package com.prueba.homeworkapp.modules.user.controllers;

import com.prueba.homeworkapp.modules.user.models.mappers.UserMapper;
import com.prueba.homeworkapp.modules.user.models.requests.UserPatchRequest;
import com.prueba.homeworkapp.modules.user.models.requests.UserRequest;
import com.prueba.homeworkapp.modules.user.models.responses.ProfileResponse;
import com.prueba.homeworkapp.modules.user.models.responses.UserResponse;
import com.prueba.homeworkapp.modules.user.models.dtos.User;
import com.prueba.homeworkapp.modules.user.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = UserController.CONTROLLER_PATH)
@Validated
@Tag(name = UserController.SWAGGER_TAG)
@RequiredArgsConstructor
public class UserController {
    public static final String SWAGGER_TAG = "User API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/user";

    private final UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(
            @Parameter(hidden = true) @AuthenticationPrincipal final Jwt principal
    ) {
        final User user = userService.getUser(
                UUID.fromString(principal.getSubject()));
        return ResponseEntity.ok(userMapper.dtoToResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(final UUID id) {
        final User user = userService.getUser(id);
        return ResponseEntity.ok(userMapper.dtoToProfileResponse(user));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody @Valid UserRequest userRequest,
            @Parameter(hidden = true) @AuthenticationPrincipal final Jwt principal
    ) {
        final User user = userMapper.requestToDto(userRequest);
        user.setId(UUID.fromString(principal.getSubject()));
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
