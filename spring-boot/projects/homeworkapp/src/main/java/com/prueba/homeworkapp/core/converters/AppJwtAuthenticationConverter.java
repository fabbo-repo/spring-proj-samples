package com.prueba.homeworkapp.core.converters;

import com.prueba.homeworkapp.modules.user.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

@RequiredArgsConstructor
public class AppJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter
            = new JwtGrantedAuthoritiesConverter();

    private final UserService userService;

    @Override
    public AbstractAuthenticationToken convert(
            @NonNull final Jwt jwt
    ) {
        final Collection<GrantedAuthority> authorities = this.jwtGrantedAuthoritiesConverter.convert(jwt);
        final String principalClaimValue = jwt.getClaimAsString("sub");
        userService.createUserIfNotExists(jwt);
        return new JwtAuthenticationToken(jwt, authorities, principalClaimValue);
    }

    public void setJwtGrantedAuthoritiesConverter(
            @NonNull final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter
    ) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
    }
}