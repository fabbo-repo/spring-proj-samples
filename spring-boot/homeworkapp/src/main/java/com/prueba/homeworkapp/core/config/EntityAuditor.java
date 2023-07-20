package com.prueba.homeworkapp.core.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(EntityAuditor.AUDITOR_BEAN_NAME)
public class EntityAuditor implements AuditorAware<String> {
    public static final String AUDITOR_BEAN_NAME = "entityAuditor";

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }
}
