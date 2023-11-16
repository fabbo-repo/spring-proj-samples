package com.mercadonatest.eanapi.modules.auth.domain.services;

import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequest;

public interface AuthService {
    TokensDto access(final CredentialsRequest credentialsRequest);

    TokensDto refresh(final String refreshToken);

    Void logout(final String refreshToken);
}
