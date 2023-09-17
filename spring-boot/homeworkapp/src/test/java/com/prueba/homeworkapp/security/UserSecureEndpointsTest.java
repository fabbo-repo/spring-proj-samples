package com.prueba.homeworkapp.security;

import com.prueba.homeworkapp.modules.user.application.controllers.UserController;
import com.prueba.homeworkapp.modules.user.domain.services.UserService;
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

import static com.prueba.homeworkapp.TestUtils.randomUuid;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserSecureEndpointsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Test
    void checkAllUserEndpointsAreSecured() throws Exception {
        final Field[] allFields = UserController.class.getDeclaredFields();

        for (final Field field : allFields) {
            if (field.getName().endsWith("PATH")) {
                final String value = String.valueOf(field.get(null));
                final String subPath = value.contains("{id}") ?
                                       value.replaceAll(
                                               "\\{id\\}",
                                               randomUuid().toString()
                                       ) : value;
                final ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                UserController.CONTROLLER_PATH + subPath
                        )
                );
                result.andExpect(status().isUnauthorized());
            }
        }
    }
}
