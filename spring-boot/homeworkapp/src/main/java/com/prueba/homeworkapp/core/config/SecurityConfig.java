package com.prueba.homeworkapp.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${oauth2.resourceserver.jwt.issuer-uri:}")
    private String issuer;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // by default a bean with "corsConfigurationSource" as name will be used
                .csrf(
                        httpSecurityCsrfConfigurer ->
                                httpSecurityCsrfConfigurer.ignoringRequestMatchers("/task/**")
                )
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/task/**"
                                        ).permitAll()
                                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
