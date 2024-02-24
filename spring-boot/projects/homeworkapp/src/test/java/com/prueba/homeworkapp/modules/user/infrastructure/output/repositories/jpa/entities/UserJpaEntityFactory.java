package com.prueba.homeworkapp.modules.user.infrastructure.output.repositories.jpa.entities;

import com.prueba.homeworkapp.modules.user.domain.models.entities.UserJpaEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomEmail;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomInt;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomText;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomUuid;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserJpaEntityFactory {
    public static UserJpaEntity.UserJpaEntityBuilder userJpaEntityBuilder() {
        return UserJpaEntity
                .builder()
                .id(randomUuid())
                .username(randomText(3, 10))
                .email(randomEmail())
                .firstName(randomText(20))
                .lastName(randomText(20))
                .age(randomInt(0, 100));
    }

    public static UserJpaEntity userJpaEntity() {
        return userJpaEntityBuilder().build();
    }
}
