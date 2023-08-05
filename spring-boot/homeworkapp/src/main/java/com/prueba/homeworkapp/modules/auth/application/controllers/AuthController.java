package com.prueba.homeworkapp.modules.auth.application.controllers;

import com.prueba.homeworkapp.modules.auth.application.models.mappers.AccessControllerMapper;
import com.prueba.homeworkapp.modules.auth.application.models.mappers.RefreshControllerMapper;
import com.prueba.homeworkapp.modules.auth.application.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.application.models.requests.RefreshRequest;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.domain.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = AuthController.CONTROLLER_PATH)
@Validated
@Tag(name = "Auth API")
@RequiredArgsConstructor
public class AuthController {
    public static final String CONTROLLER_PATH = "/auth";

    private final AuthService authService;

    private final AccessControllerMapper accessMapper = AccessControllerMapper.INSTANCE;

    private final RefreshControllerMapper refreshMapper = RefreshControllerMapper.INSTANCE;

    @PostMapping("/access")
    public ResponseEntity<UserAndJwts> getAccessToken(
            @RequestBody @Valid AccessRequest accessRequest
    ) {
        final Access access = accessMapper.requestToDto(accessRequest);
        return ResponseEntity.ok(authService.access(access));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Jwts> refreshToken(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = refreshMapper.requestToDto(refreshRequest);
        return ResponseEntity.ok(authService.refresh(refresh));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = refreshMapper.requestToDto(refreshRequest);
        authService.logout(refresh);
        return ResponseEntity.noContent().build();
    }
}
