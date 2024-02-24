package com.prueba.homeworkapp.modules.auth.application.usecases;

import com.prueba.homeworkapp.modules.auth.domain.models.Jwts;
import com.prueba.homeworkapp.modules.auth.domain.models.Refresh;

public interface RefreshUseCase {
    Jwts refresh(final Refresh refresh);
}
