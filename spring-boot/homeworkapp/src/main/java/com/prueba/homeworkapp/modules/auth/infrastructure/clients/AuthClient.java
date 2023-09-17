package com.prueba.homeworkapp.modules.auth.infrastructure.clients;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;

public interface AuthClient {
    Jwts access(String username, String password);

    Jwts refresh(String refreshToken);

    Void logout(String refreshToken);

    JsonObject decodeToken(String token);
}
