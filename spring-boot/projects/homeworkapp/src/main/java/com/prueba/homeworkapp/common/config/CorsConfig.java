package com.prueba.homeworkapp.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class CorsConfig {
    @Value("#{'${api.cors.allowed-origins:}'}")
    private String allowedOrigins;

    @Value("${api.cors.allowed-origin-patterns}")
    private String allowedOriginPatterns;

    @Value("${api.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${api.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${api.cors.allow-credentials}")
    private String allowCredentials;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        if (!allowedOrigins.isEmpty()) {
            corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        } else {
            corsConfiguration.setAllowedOriginPatterns(Arrays.asList(allowedOriginPatterns.split(",")));
        }
        corsConfiguration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
        corsConfiguration.setAllowCredentials(Boolean.parseBoolean(allowCredentials));
        corsConfiguration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}