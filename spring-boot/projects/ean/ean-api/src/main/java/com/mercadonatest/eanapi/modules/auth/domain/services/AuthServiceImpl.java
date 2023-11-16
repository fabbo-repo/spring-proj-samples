package com.mercadonatest.eanapi.modules.auth.domain.services;

import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequest;
import com.mercadonatest.eanapi.modules.auth.domain.clients.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;

    @Override
    public TokensDto access(final CredentialsRequest credentialsRequest) {
        return authClient.access(
                credentialsRequest.username(),
                credentialsRequest.password()
        );
    }

    @Override
    public TokensDto refresh(final String refreshToken) {
        return authClient.refresh(refreshToken);
    }

    @Override
    public Void logout(final String refreshToken) {
        authClient.logout(refreshToken);
        return null;
    }
}
