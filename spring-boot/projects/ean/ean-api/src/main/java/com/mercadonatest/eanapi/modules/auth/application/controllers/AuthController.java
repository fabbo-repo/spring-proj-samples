package com.mercadonatest.eanapi.modules.auth.application.controllers;

import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequest;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.RefreshTokenRequest;
import com.mercadonatest.eanapi.modules.auth.domain.services.AuthService;
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

    @PostMapping(POST_ACCESS_SUB_PATH)
    public ResponseEntity<TokensDto> getAccessToken(
            @RequestBody @Valid CredentialsRequest credentialsRequest
    ) {
        final TokensDto tokens = authService.access(credentialsRequest);
        return ResponseEntity
                .ok()
                .body(tokens);
    }

    @PostMapping(POST_REFRESH_SUB_PATH)
    public ResponseEntity<TokensDto> refreshToken(
            @RequestBody @Valid RefreshTokenRequest refreshRequest
    ) {
        final TokensDto tokens = authService.refresh(refreshRequest.refreshToken());
        return ResponseEntity
                .ok()
                .body(tokens);
    }

    @PostMapping(POST_LOGOUT_SUB_PATH)
    public ResponseEntity<Void> logout(
            @RequestBody @Valid RefreshTokenRequest refreshRequest
    ) {
        authService.logout(refreshRequest.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
