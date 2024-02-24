package com.prueba.homeworkapp.modules.auth.application.usecases;

import com.prueba.homeworkapp.modules.auth.domain.models.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.Jwts;

public interface AccessUseCase {
    Jwts access(final Access access);
}
