package com.prueba.homeworkapp.security;

import com.prueba.homeworkapp.modules.task.application.controllers.TaskController;
import com.prueba.homeworkapp.modules.task.domain.sevices.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;

import static com.prueba.homeworkapp.TestUtils.randomStatusEnum;
import static com.prueba.homeworkapp.TestUtils.randomUuid;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
class TaskSecureEndpointsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TaskService taskService;

    @Test
    void checkAllTaskEndpointsAreSecured() throws Exception {
        final Field[] allFields = TaskController.class.getDeclaredFields();

        for (final Field field : allFields) {
            if (field.getName().endsWith("PATH")) {
                final String value = String.valueOf(field.get(null));
                final String subPath = value.contains("{id}") ?
                                       value.replaceAll(
                                               "\\{id\\}",
                                               randomUuid().toString()
                                       ) : value.contains("{status}") ?
                                           value.replaceAll(
                                                   "\\{status\\}",
                                                   randomStatusEnum().name()
                                           ) : value;
                final ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                TaskController.CONTROLLER_PATH + subPath
                        )
                );
                result.andExpect(status().isUnauthorized());
            }
        }
    }
}
