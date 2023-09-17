package com.prueba.homeworkapp.modules.auth.domain.models.dtos;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.TestUtils.randomEmail;
import static com.prueba.homeworkapp.TestUtils.randomInt;
import static com.prueba.homeworkapp.TestUtils.randomText;
import static com.prueba.homeworkapp.TestUtils.randomUuid;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAndJwtsFactory {
    public static UserAndJwts.UserAndJwtsBuilder userAndJwtsBuilder() {
        return UserAndJwts
                .builder()
                .id(randomUuid())
                .username(randomText())
                .email(randomEmail())
                .firstName(randomText())
                .lastName(randomText())
                .age(randomInt())
                .accessToken(randomText())
                .refreshToken(randomText());
    }

    public static UserAndJwts userAndJwts() {
        return userAndJwtsBuilder().build();
    }
}
