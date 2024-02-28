package com.prueba.homeworkapp.common.config;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.common.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@Profile({HomeworkappApplication.NON_TEST_PROFILE})
@EnableJpaAuditing(auditorAwareRef = JpaAuditConfig.AUDITOR_BEAN_NAME)
public class JpaAuditConfig {
    public static final String AUDITOR_BEAN_NAME = "auditorBean";

    @Bean(name = AUDITOR_BEAN_NAME)
    @Profile({HomeworkappApplication.NON_TEST_PROFILE})
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
