package com.prueba.homeworkapp.modules.auth.domain.services;

import com.prueba.homeworkapp.modules.auth.domain.clients.AuthAdminClient;
import com.prueba.homeworkapp.modules.auth.domain.clients.AuthClient;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

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
