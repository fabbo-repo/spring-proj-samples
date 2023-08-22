package com.prueba.homeworkapp.modules.auth.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.homeworkapp.ReplaceUnderscoresAndCamelCase;
import com.prueba.homeworkapp.modules.auth.application.models.mappers.AccessControllerMapper;
import com.prueba.homeworkapp.modules.auth.application.models.requests.AccessRequest;
import com.prueba.homeworkapp.modules.auth.application.models.requests.AccessRequestFactory;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.Access;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwts;
import com.prueba.homeworkapp.modules.auth.domain.models.dtos.UserAndJwtsFactory;
import com.prueba.homeworkapp.modules.auth.domain.services.AuthService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private final AccessControllerMapper accessMapper = AccessControllerMapper.INSTANCE;

    @Test
    void givenValidAccessRequest_whenGetAccessToken_shouldReturn200Response() throws Exception {
        final UserAndJwts expectedResponse = UserAndJwtsFactory.userAndJwts();
        final AccessRequest request = AccessRequestFactory.accessRequestBuilder()
                                                          .email(expectedResponse.getEmail())
                                                          .build();
        final Access access = accessMapper.requestToDto(request);

        when(authService.access(access))
                .thenReturn(expectedResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(String.format(
                                "%s%s",
                                AuthController.CONTROLLER_PATH,
                                AuthController.POST_ACCESS_SUB_PATH
                        ))
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Check response http status code
        result.andExpect(status().isOk());

        final MvcResult mvcResult = result.andReturn();
        final String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResponse));
    }

}
