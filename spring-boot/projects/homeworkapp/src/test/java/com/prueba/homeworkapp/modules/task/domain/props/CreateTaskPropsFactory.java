package com.prueba.homeworkapp.modules.task.domain.props;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.prueba.homeworkapp.common.utils.TestDataUtils.randomBool;
import static com.prueba.homeworkapp.common.utils.TestDataUtils.randomFutureDateTime;
import static com.prueba.homeworkapp.common.utils.TestDataUtils.randomText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTaskPropsFactory {

    public static CreateTaskProps createTaskProps() {
        return new CreateTaskProps(
                randomText(3, 30),
                randomText(0, 500),
                randomFutureDateTime(),
                randomFutureDateTime(),
                randomBool()
        );
    }
}
