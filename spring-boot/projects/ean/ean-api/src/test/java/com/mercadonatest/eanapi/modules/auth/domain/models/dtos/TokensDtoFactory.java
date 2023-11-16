package com.mercadonatest.eanapi.modules.auth.domain.models.dtos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.mercadonatest.eanapi.TestUtils.randomJwt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokensDtoFactory {
    public static TokensDto.TokensDtoBuilder tokensDtoBuilder() {
        return TokensDto
                .builder()
                .accessToken(randomJwt("test"))
                .refreshToken(randomJwt("test"));
    }

    public static TokensDto tokensDto() {
        return tokensDtoBuilder().build();
    }
}