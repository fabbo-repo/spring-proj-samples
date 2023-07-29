package com.prueba.homeworkapp.modules.auth.domain.clients;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;

public interface AuthClient {
    Jwts access(final String username, final String password);

    Jwts refresh(final String refreshToken);

    void logout(final String refreshToken);
}
