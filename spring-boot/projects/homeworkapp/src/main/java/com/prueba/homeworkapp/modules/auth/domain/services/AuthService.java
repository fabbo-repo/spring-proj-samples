package com.prueba.homeworkapp.modules.auth.domain.services;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Refresh;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;

public interface AuthService {
    UserAndJwts access(final Access access);

    Jwts refresh(final Refresh refresh);

    void logout(final Refresh refresh);
}
