package com.prueba.homeworkapp.modules.auth.controllers;

import com.prueba.homeworkapp.modules.auth.models.mappers.AccessMapper;
import com.prueba.homeworkapp.modules.auth.models.mappers.RefreshMapper;
import com.prueba.homeworkapp.modules.auth.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.models.requests.RefreshRequest;
import com.prueba.homeworkapp.modules.auth.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.services.AuthService;
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
@Tag(name = AuthController.SWAGGER_TAG)
@RequiredArgsConstructor
public class AuthController {
    public static final String SWAGGER_TAG = "Auth API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/auth";
    @SuppressWarnings("squid:S1075")
    public static final String POST_ACCESS_SUB_PATH = "/access";
    @SuppressWarnings("squid:S1075")
    public static final String POST_REFRESH_SUB_PATH = "/refresh";
    @SuppressWarnings("squid:S1075")
    public static final String POST_LOGOUT_SUB_PATH = "/logout";

    private final AuthService authService;

    private final AccessMapper accessMapper = AccessMapper.INSTANCE;

    private final RefreshMapper refreshMapper = RefreshMapper.INSTANCE;

    @PostMapping(POST_ACCESS_SUB_PATH)
    public ResponseEntity<UserAndJwts> getAccessToken(
            @RequestBody @Valid AccessRequest accessRequest
    ) {
        final Access access = accessMapper.requestToDto(accessRequest);
        return ResponseEntity.ok(authService.access(access));
    }

    @PostMapping(POST_REFRESH_SUB_PATH)
    public ResponseEntity<Jwts> refreshToken(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = refreshMapper.requestToDto(refreshRequest);
        return ResponseEntity.ok(authService.refresh(refresh));
    }

    @PostMapping(POST_LOGOUT_SUB_PATH)
    public ResponseEntity<Void> logout(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = refreshMapper.requestToDto(refreshRequest);
        authService.logout(refresh);
        return ResponseEntity.noContent().build();
    }
}
