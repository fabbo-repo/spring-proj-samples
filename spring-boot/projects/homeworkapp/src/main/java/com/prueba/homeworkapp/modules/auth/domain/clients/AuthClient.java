package com.prueba.homeworkapp.modules.auth.domain.clients;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;

public interface AuthClient {
    Jwts access(String username, String password);

    Jwts refresh(String refreshToken);

    Void logout(String refreshToken);
}
