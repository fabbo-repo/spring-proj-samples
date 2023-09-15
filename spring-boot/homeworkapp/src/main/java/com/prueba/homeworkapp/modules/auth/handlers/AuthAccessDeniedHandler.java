package com.prueba.homeworkapp.modules.auth.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AccessDeniedException accessDeniedException
    ) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());
    }
}
