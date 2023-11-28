package com.prueba.homeworkapp.modules.auth.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.homeworkapp.HomeworkappApplication;
import com.prueba.homeworkapp.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.AccessMapper;
import com.prueba.homeworkapp.modules.auth.domain.models.mappers.RefreshMapper;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.domain.models.requests.AccessRequestFactory;
import com.prueba.homeworkapp.modules.auth.domain.services.AuthService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.prueba.homeworkapp.TestUtils.randomText;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(HomeworkappApplication.TEST_PROFILE)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private final AccessMapper accessMapper = AccessMapper.INSTANCE;

    private final RefreshMapper refreshMapper = RefreshMapper.INSTANCE;

    public static final String POST_ACCESS_URL = AuthController.CONTROLLER_PATH
                                                 + AuthController.POST_ACCESS_SUB_PATH;

    private static final String POST_REFRESH_URL = AuthController.CONTROLLER_PATH +
                                                   AuthController.POST_REFRESH_SUB_PATH;

    private static final String POST_LOGOUT_URL = AuthController.CONTROLLER_PATH
                                                  + AuthController.POST_LOGOUT_SUB_PATH;

    @Test
    void givenInvalidAccessRequest_whenPostAccess_shouldReturn401Response() throws Exception {
        final AccessRequest request = AccessRequestFactory.accessRequest();

        final UnauthorizedException exception = new UnauthorizedException(
                randomText()
        );

        when(authService.access(accessMapper.requestToDto(request)))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_ACCESS_URL)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnauthorized());
    }
}
