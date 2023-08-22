package com.prueba.homeworkapp.modules.auth.application.models.requests;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.TestUtils.randomEmail;
import static com.prueba.homeworkapp.TestUtils.randomText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessRequestFactory {
    public static AccessRequest.AccessRequestBuilder accessRequestBuilder() {
        return AccessRequest
                .builder()
                .email(randomEmail())
                .password(randomText(300));
    }

    public static AccessRequest accessRequest() {
        return accessRequestBuilder().build();
    }
}