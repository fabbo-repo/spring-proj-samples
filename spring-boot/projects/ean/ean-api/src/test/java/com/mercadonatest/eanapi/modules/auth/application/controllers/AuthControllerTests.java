package com.mercadonatest.eanapi.modules.auth.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadonatest.eanapi.EanApiApplication;
import com.mercadonatest.eanapi.ReplaceUnderscoresAndCamelCase;
import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDto;
import com.mercadonatest.eanapi.modules.auth.domain.models.dtos.TokensDtoFactory;
import com.mercadonatest.eanapi.modules.auth.domain.models.exceptions.UnauthorizedException;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequest;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.CredentialsRequestFactory;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.RefreshTokenRequest;
import com.mercadonatest.eanapi.modules.auth.domain.models.requests.RefreshTokenRequestFactory;
import com.mercadonatest.eanapi.modules.auth.domain.services.AuthService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.mercadonatest.eanapi.TestUtils.randomText;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(EanApiApplication.TEST_PROFILE)
class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    public static final String POST_ACCESS_URL = AuthController.CONTROLLER_PATH
                                                 + AuthController.POST_ACCESS_SUB_PATH;

    private static final String POST_REFRESH_URL = AuthController.CONTROLLER_PATH +
                                                   AuthController.POST_REFRESH_SUB_PATH;

    private static final String POST_LOGOUT_URL = AuthController.CONTROLLER_PATH
                                                  + AuthController.POST_LOGOUT_SUB_PATH;

    @Test
    void givenValidCredentialsRequest_whenGetAccessToken_shouldReturn200Response() throws Exception {
        final TokensDto expectedResponse = TokensDtoFactory.tokensDto();
        final CredentialsRequest request = CredentialsRequestFactory.credentialsRequest();

        when(authService.access(request))
                .thenReturn(expectedResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_ACCESS_URL)
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

    @Test
    void givenInvalidCredentialsRequest_whenPostAccess_shouldReturn401Response() throws Exception {
        final CredentialsRequest request = CredentialsRequestFactory.credentialsRequest();

        final UnauthorizedException exception = new UnauthorizedException(
                randomText()
        );

        when(authService.access(request))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_ACCESS_URL)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void givenValidRefreshRequest_whenPostRefresh_shouldReturn200AndTokens() throws Exception {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();
        final TokensDto expectedResponse = TokensDtoFactory.tokensDto();

        when(authService.refresh(request.refreshToken()))
                .thenReturn(expectedResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_REFRESH_URL)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());

        final MvcResult mvcResult = result.andReturn();
        final String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    void givenInvalidRefreshRequest_whenPostRefresh_shouldReturn401Response() throws Exception {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();
        final UnauthorizedException exception = new UnauthorizedException(
                randomText()
        );

        when(authService.refresh(request.refreshToken()))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_REFRESH_URL)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void givenValidRefreshRequest_whenPostLogout_shouldReturn204() throws Exception {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_LOGOUT_URL)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNoContent());
    }

    @Test
    void givenInvalidRefreshRequest_whenPostLogout_shouldReturn401Response() throws Exception {
        final RefreshTokenRequest request = RefreshTokenRequestFactory.refreshTokenRequest();
        final UnauthorizedException exception = new UnauthorizedException(
                randomText()
        );

        when(authService.logout(request.refreshToken()))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_LOGOUT_URL)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnauthorized());
    }
}
