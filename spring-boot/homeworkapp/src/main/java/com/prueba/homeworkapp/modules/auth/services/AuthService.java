package com.prueba.homeworkapp.modules.auth.services;

import com.prueba.homeworkapp.modules.auth.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.models.dtos.UserAndJwts;

public interface AuthService {
    UserAndJwts access(final Access access);

    Jwts refresh(final Refresh refresh);

    void logout(final Refresh refresh);
}
