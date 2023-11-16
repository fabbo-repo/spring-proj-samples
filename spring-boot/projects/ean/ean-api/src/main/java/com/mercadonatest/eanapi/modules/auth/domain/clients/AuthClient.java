package com.mercadonatest.eanapi.modules.auth.domain.clients;

import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;

public interface AuthClient {
    TokensDto access(String username, String password);

    TokensDto refresh(String refreshToken);

    Void logout(String refreshToken);
}
