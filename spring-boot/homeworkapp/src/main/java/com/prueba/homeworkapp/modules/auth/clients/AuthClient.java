package com.prueba.homeworkapp.modules.auth.clients;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.prueba.homeworkapp.modules.auth.models.dtos.Jwts;

public interface AuthClient {
    Jwts access(String username, String password);

    Jwts refresh(String refreshToken);

    void logout(String refreshToken);

    JsonObject decodeToken(String token);
}
