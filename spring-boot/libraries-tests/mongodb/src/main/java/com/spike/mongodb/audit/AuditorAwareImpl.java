package com.spike.mongodb.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        //final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.of(
                "test"
                //authentication == null ? null : authentication.getName()
        );
    }
}