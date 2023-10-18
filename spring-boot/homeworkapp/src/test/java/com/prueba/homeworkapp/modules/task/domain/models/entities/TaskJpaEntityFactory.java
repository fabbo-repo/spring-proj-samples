package com.prueba.homeworkapp.modules.task.domain.models.entities;

import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.TestUtils.randomBool;
import static com.prueba.homeworkapp.TestUtils.randomEmail;
import static com.prueba.homeworkapp.TestUtils.randomFutureDateTime;
import static com.prueba.homeworkapp.TestUtils.randomInt;
import static com.prueba.homeworkapp.TestUtils.randomPastDateTime;
import static com.prueba.homeworkapp.TestUtils.randomStatusEnum;
import static com.prueba.homeworkapp.TestUtils.randomText;
import static com.prueba.homeworkapp.TestUtils.randomUuid;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskJpaEntityFactory {
    public static TaskJpaEntity.TaskJpaEntityBuilder taskJpaEntityBuilder() {
        return TaskJpaEntity
                .builder()
                .id(randomUuid())
                .title(randomText(3, 30))
                .description(randomText(500))
                .estimatedDoneAt(randomFutureDateTime())
                .finishedAt(randomPastDateTime())
                .finished(randomBool())
                .taskStatus(randomStatusEnum());
    }

    public static TaskJpaEntity taskJpaEntity() {
        return taskJpaEntityBuilder().build();
    }
}
