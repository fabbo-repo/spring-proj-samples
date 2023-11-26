package com.spike.mongodb.config;

import com.spike.mongodb.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing(auditorAwareRef = MongodbAuditConfig.AUDITOR_BEAN_NAME)
public class MongodbAuditConfig {
    public static final String AUDITOR_BEAN_NAME = "auditorBean";

    @Bean(name = AUDITOR_BEAN_NAME)
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
