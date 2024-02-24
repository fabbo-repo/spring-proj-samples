package com.prueba.homeworkapp.commons.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return getToken()
                .map(JwtAuthenticationToken::getName);
    }

    public Optional<JwtAuthenticationToken> getToken() {
        final Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        return Optional.of((JwtAuthenticationToken) authentication);
    }
}
