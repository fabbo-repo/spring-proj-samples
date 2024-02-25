package com.spikes.webflux.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class AppJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(
            @NonNull final Jwt jwt
    ) {
        final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter
                = new JwtGrantedAuthoritiesConverter();
        final Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        final String principalClaimValue = jwt.getClaimAsString("sub");
        return Mono.just(
                new JwtAuthenticationToken(jwt, authorities, principalClaimValue)
        );
    }
}