package com.prueba.homeworkapp.core.config;

import com.prueba.homeworkapp.core.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = JpaAuditConfig.AUDITOR_BEAN_NAME)
public class JpaAuditConfig {
    public static final String AUDITOR_BEAN_NAME = "auditorBean";

    @Bean(name = AUDITOR_BEAN_NAME)
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
