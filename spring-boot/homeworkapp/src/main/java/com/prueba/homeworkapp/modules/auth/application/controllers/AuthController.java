package com.prueba.homeworkapp.modules.auth.application.controllers;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.AccessMapper;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.RefreshMapper;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.RefreshRequest;
import com.prueba.homeworkapp.modules.auth.domain.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    public static final String REFRESH_COOKIE_NAME = "refreshToken";

    @Value("${api.cookie.http-only}")
    private boolean cookieHttpOnly;

    @Value("${api.cookie.same-site}")
    private String cookieSameSite;

    @Value("${api.cookie.secure}")
    private boolean cookieSecure;

    private final AuthService authService;

    private final AccessMapper accessMapper = AccessMapper.INSTANCE;

    private final RefreshMapper refreshMapper = RefreshMapper.INSTANCE;

    @PostMapping(POST_ACCESS_SUB_PATH)
    public ResponseEntity<UserAndJwts> getAccessToken(
            @RequestBody @Valid AccessRequest accessRequest
    ) {
        final Access access = accessMapper.requestToDto(accessRequest);
        final UserAndJwts userAndJwts = authService.access(access);
        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        refreshTokenCookie(
                                userAndJwts.getRefreshToken(),
                                userAndJwts.getRefreshExpiresIn()
                        )
                )
                .body(userAndJwts);
    }

    @PostMapping(POST_REFRESH_SUB_PATH)
    public ResponseEntity<Jwts> refreshToken(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = refreshMapper.requestToDto(refreshRequest);
        final Jwts jwts = authService.refresh(refresh);
        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        refreshTokenCookie(
                                jwts.getRefreshToken(),
                                jwts.getRefreshExpiresIn()
                        )
                )
                .body(jwts);
    }

    @PostMapping(POST_LOGOUT_SUB_PATH)
    public ResponseEntity<Void> logout(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = refreshMapper.requestToDto(refreshRequest);
        authService.logout(refresh);
        return ResponseEntity.noContent().build();
    }

    private String refreshTokenCookie(final String refreshToken, final long refreshExpiresIn) {
        return ResponseCookie
                .from(REFRESH_COOKIE_NAME, refreshToken)
                .httpOnly(cookieHttpOnly)
                .maxAge(refreshExpiresIn)
                .sameSite(cookieSameSite)
                .secure(cookieSecure)
                .build()
                .toString();
    }
}
