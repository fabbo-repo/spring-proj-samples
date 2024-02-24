package com.prueba.homeworkapp.modules.auth.application.usecases;

import com.prueba.homeworkapp.modules.auth.domain.models.Refresh;

public interface LogoutUseCase {
    void logout(final Refresh refresh);
}
