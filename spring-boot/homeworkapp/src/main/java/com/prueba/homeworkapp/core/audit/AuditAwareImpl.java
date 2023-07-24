package com.prueba.homeworkapp.core.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(AuditAwareImpl.AUDITOR_BEAN_NAME)
public class AuditAwareImpl implements AuditorAware<String> {
    public static final String AUDITOR_BEAN_NAME = "auditorBean";

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }
}
