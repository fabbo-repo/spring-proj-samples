package com.mercadonatest.eanapi.core.config;

import com.mercadonatest.eanapi.EanApiApplication;
import com.mercadonatest.eanapi.core.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@Profile({EanApiApplication.NON_TEST_PROFILE})
@EnableJpaAuditing(auditorAwareRef = JpaAuditConfig.AUDITOR_BEAN_NAME)
public class JpaAuditConfig {
    public static final String AUDITOR_BEAN_NAME = "auditorBean";

    @Bean(name = AUDITOR_BEAN_NAME)
    @Profile({EanApiApplication.NON_TEST_PROFILE})
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
