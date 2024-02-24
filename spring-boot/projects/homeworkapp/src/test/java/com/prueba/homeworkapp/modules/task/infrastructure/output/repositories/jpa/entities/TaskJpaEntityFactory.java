package com.prueba.homeworkapp.modules.task.infrastructure.output.repositories.jpa.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomBool;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomFutureDateTime;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomPastDateTime;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomStatusEnum;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomText;
import static com.prueba.homeworkapp.commons.utils.TestDataUtils.randomUuid;

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
