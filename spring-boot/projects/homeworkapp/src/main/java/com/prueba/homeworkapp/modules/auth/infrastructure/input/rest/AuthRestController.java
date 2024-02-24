package com.prueba.homeworkapp.modules.auth.infrastructure.input.rest;

import com.prueba.homeworkapp.modules.auth.application.usecases.AccessUseCase;
import com.prueba.homeworkapp.modules.auth.application.usecases.LogoutUseCase;
import com.prueba.homeworkapp.modules.auth.application.usecases.RefreshUseCase;
import com.prueba.homeworkapp.modules.auth.domain.models.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.Refresh;
import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.mappers.AccessRestMapper;
import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.mappers.RefreshRestMapper;
import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests.RefreshRequest;
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
@RequestMapping(path = AuthRestController.CONTROLLER_PATH)
@Validated
@Tag(name = AuthRestController.SWAGGER_TAG)
@RequiredArgsConstructor
public class AuthRestController {
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

    private final AccessUseCase accessUseCase;

    private final RefreshUseCase refreshUseCase;

    private final LogoutUseCase logoutUseCase;

    private static final AccessRestMapper ACCESS_REST_MAPPER = AccessRestMapper.INSTANCE;

    private static final RefreshRestMapper REFRESH_REST_MAPPER = RefreshRestMapper.INSTANCE;

    @PostMapping(POST_ACCESS_SUB_PATH)
    public ResponseEntity<Jwts> getAccessToken(
            @RequestBody @Valid AccessRequest accessRequest
    ) {
        final Access access = ACCESS_REST_MAPPER.accessRequestToAccess(accessRequest);
        final Jwts jwts = accessUseCase.access(access);
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

    @PostMapping(POST_REFRESH_SUB_PATH)
    public ResponseEntity<Jwts> refreshToken(
            @RequestBody @Valid RefreshRequest refreshRequest
    ) {
        final Refresh refresh = REFRESH_REST_MAPPER.refreshRequestToRefresh(refreshRequest);
        final Jwts jwts = refreshUseCase.refresh(refresh);
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
        final Refresh refresh = REFRESH_REST_MAPPER.refreshRequestToRefresh(refreshRequest);
        logoutUseCase.logout(refresh);
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
