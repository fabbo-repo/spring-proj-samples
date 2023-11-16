package com.mercadonatest.eanapi.modules.auth.domain.models.requests;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.mercadonatest.eanapi.TestUtils.randomJwt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenRequestFactory {

    public static RefreshTokenRequest.RefreshTokenRequestBuilder refreshTokenRequestBuilder() {
        return RefreshTokenRequest
                .builder()
                .refreshToken(randomJwt("test"));
    }

    public static RefreshTokenRequest refreshTokenRequest() {
        return refreshTokenRequestBuilder().build();
    }
}
