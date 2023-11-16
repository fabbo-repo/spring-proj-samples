package com.mercadonatest.eanapi.modules.auth.domain.models.requests;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.mercadonatest.eanapi.TestUtils.randomText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CredentialsRequestFactory {

    public static CredentialsRequest.CredentialsRequestBuilder credentialsRequestBuilder() {
        return CredentialsRequest
                .builder()
                .username(randomText(1, 100))
                .password(randomText(1, 100));
    }

    public static CredentialsRequest credentialsRequest() {
        return credentialsRequestBuilder().build();
    }
}
