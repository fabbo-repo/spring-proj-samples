package com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests;

import com.prueba.homeworkapp.modules.auth.infrastructure.input.rest.data.requests.AccessRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomEmail;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomText;

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