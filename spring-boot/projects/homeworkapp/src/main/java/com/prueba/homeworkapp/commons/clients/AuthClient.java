package com.prueba.homeworkapp.commons.clients;

import com.prueba.homeworkapp.modules.auth.domain.models.Jwts;

public interface AuthClient {
    Jwts access(String username, String password);

    Jwts refresh(String refreshToken);

    Void logout(String refreshToken);
}
