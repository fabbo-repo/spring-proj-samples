package com.spikes.webflux.config;

import com.spikes.webflux.converters.AppJwtAuthenticationConverter;
import com.spikes.webflux.handlers.AccessDeniedHandler;
import com.spikes.webflux.handlers.AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            final ServerHttpSecurity http
    ) {
        http
                .cors(withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        auth -> {
                            // Example urls
                            auth.pathMatchers("/actuator/health/**")
                                .permitAll();
                            auth.pathMatchers("/swagger-ui/**")
                                .permitAll();
                            auth.pathMatchers("/v3/api-docs/**")
                                .permitAll();
                            auth.pathMatchers("/auth/**")
                                .permitAll();
                            auth.pathMatchers("/public/**")
                                .permitAll();
                            auth.anyExchange()
                                .authenticated();
                        }
                )
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer ->
                                httpSecurityExceptionHandlingConfigurer
                                        .accessDeniedHandler(
                                                new AccessDeniedHandler()
                                        )
                                        .authenticationEntryPoint(
                                                new AuthenticationEntryPoint()
                                        )
                )
                .oauth2ResourceServer(
                        oAuth2ResourceServerSpec ->
                                oAuth2ResourceServerSpec
                                        .jwt(
                                                jwtConfigurer ->
                                                        jwtConfigurer
                                                                .jwtAuthenticationConverter(
                                                                        jwtAuthenticationConverter())
                                        )
                );
        return http.build();
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        return new AppJwtAuthenticationConverter();
    }
}
