package com.prueba.homeworkapp.core.config;

import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.modules.auth.application.handlers.AuthAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    @Profile(HomeworkappApplication.NON_TEST_PROFILE)
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // by default a bean with "corsConfigurationSource" as name will be used
                .csrf(
                        httpSecurityCsrfConfigurer ->
                                httpSecurityCsrfConfigurer.ignoringRequestMatchers("/auth/**")
                )
                .sessionManagement(session ->
                                           session
                                                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/auth/**"
                                        ).permitAll()
                                        .anyRequest().authenticated()
                )
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer ->
                                httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(
                                        new AuthAccessDeniedHandler()
                                )
                )
                .oauth2ResourceServer(
                        httpSecurityOAuth2ResourceServerConfigurer ->
                                httpSecurityOAuth2ResourceServerConfigurer
                                        .jwt(jwtConfigurer ->
                                                     jwtConfigurer
                                                             .jwtAuthenticationConverter(
                                                                     jwtAuthenticationConverter())
                                        )
                );
        return http.build();
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        //jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }
}
