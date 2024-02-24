package com.prueba.homeworkapp.commons.config;

import com.prueba.homeworkapp.commons.converters.AppJwtAuthenticationConverter;
import com.prueba.homeworkapp.commons.handlers.AuthAccessDeniedHandler;
import com.prueba.homeworkapp.modules.user.domain.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http,
            final ApplicationContext applicationContext
    ) throws Exception {
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
                                                                     jwtAuthenticationConverter(applicationContext))
                                        )
                );
        return http.build();
    }

    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter(
            final ApplicationContext applicationContext
    ) {
        final Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter
                = new AppJwtAuthenticationConverter(
                applicationContext.getBean(UserService.class)
        );
        //jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }
}
