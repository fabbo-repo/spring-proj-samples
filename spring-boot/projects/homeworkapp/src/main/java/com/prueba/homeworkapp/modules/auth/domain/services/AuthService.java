package com.prueba.homeworkapp.modules.auth.domain.services;

import com.prueba.homeworkapp.common.clients.AuthAdminClient;
import com.prueba.homeworkapp.common.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.application.usecases.AccessUseCase;
import com.prueba.homeworkapp.modules.auth.application.usecases.LogoutUseCase;
import com.prueba.homeworkapp.modules.auth.application.usecases.RefreshUseCase;
import com.prueba.homeworkapp.modules.auth.domain.models.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.Refresh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AccessUseCase, RefreshUseCase, LogoutUseCase {

    private final AuthClient authClient;

    private final AuthAdminClient authAdminClient;

    @Override
    public Jwts access(final Access access) {
        return authClient.access(
                access.getEmail(),
                access.getPassword()
        );
    }

    @Override
    public Jwts refresh(final Refresh refresh) {
        return authClient.refresh(refresh.getRefreshToken());
    }

    @Override
    public void logout(final Refresh refresh) {
        authClient.logout(
                refresh.getRefreshToken()
        );
    }
}
