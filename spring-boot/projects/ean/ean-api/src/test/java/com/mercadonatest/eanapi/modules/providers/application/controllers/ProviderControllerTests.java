package com.mercadonatest.eanapi.modules.providers.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadonatest.eanapi.EanApiApplication;
import com.mercadonatest.eanapi.ReplaceUnderscoresAndCamelCase;
import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.modules.provider.application.controllers.ProviderController;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;
import com.mercadonatest.eanapi.modules.provider.domain.services.ProviderService;
import com.mercadonatest.eanapi.modules.providers.domain.models.requests.ProviderRequestFactory;
import com.mercadonatest.eanapi.modules.providers.domain.models.responses.ProviderResponseFactory;
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

import java.util.ArrayList;

import static com.mercadonatest.eanapi.TestUtils.randomInt;
import static com.mercadonatest.eanapi.TestUtils.randomLong;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProviderController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(EanApiApplication.TEST_PROFILE)
class ProviderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProviderService providerService;

    public static final String POST_PROVIDER_URL = ProviderController.CONTROLLER_PATH;

    private static final String GET_PROVIDER_URL = ProviderController.CONTROLLER_PATH +
                                                   ProviderController.GET_PROVIDER_SUB_PATH;

    private static final String GET_LIST_PROVIDER_URL = ProviderController.CONTROLLER_PATH;

    private static final String PUT_PROVIDER_URL = ProviderController.CONTROLLER_PATH +
                                                   ProviderController.PUT_PROVIDER_SUB_PATH;

    private static final String DEL_PROVIDER_URL = ProviderController.CONTROLLER_PATH +
                                                   ProviderController.DEL_PROVIDER_SUB_PATH;

    @Test
    void givenProviderRequest_whenPostProvider_shouldReturn200Response() throws Exception {
        final ProviderResponse expectedResponse = ProviderResponseFactory.providerResponse();
        final ProviderRequest request = ProviderRequestFactory.providerRequest();

        when(providerService.create(request))
                .thenReturn(expectedResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_PROVIDER_URL)
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
    void givenProviderId_whenGetProvider_shouldReturn200Response() throws Exception {
        final ProviderResponse expectedResponse = ProviderResponseFactory.providerResponse();

        when(providerService.get(expectedResponse.id()))
                .thenReturn(expectedResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(GET_PROVIDER_URL, expectedResponse.id())
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
    void thrownEntityNotFound_whenGetProvider_shouldReturn400Response() throws Exception {
        final Long randomId = randomLong();
        final EntityNotFoundException exception = new EntityNotFoundException(
                ProviderJpaEntity.TABLE_NAME,
                ProviderJpaEntity.ID_COL,
                randomId
        );

        when(providerService.get(randomId))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(GET_PROVIDER_URL, randomId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    void givenRequest_whenGetProviderList_shouldReturn200Response() throws Exception {
        final int pageCount = randomInt(3, 10);
        final PageDto<ProviderResponse> expectedPageResponse = new PageDto<>(
                pageCount,
                null,
                null,
                new ArrayList<>()
        );
        for (int i = 0; i < pageCount; i++) {
            expectedPageResponse.results().add(
                    ProviderResponseFactory.providerResponse()
            );
        }

        when(providerService.list(0))
                .thenReturn(expectedPageResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(GET_LIST_PROVIDER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Check response http status code
        result.andExpect(status().isOk());

        final MvcResult mvcResult = result.andReturn();
        final String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedPageResponse));
    }

    @Test
    void givenProviderRequest_whenPutProvider_shouldReturn204Response() throws Exception {
        final ProviderRequest request = ProviderRequestFactory.providerRequest();

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .put(PUT_PROVIDER_URL, randomLong())
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Check response http status code
        result.andExpect(status().isNoContent());
    }

    @Test
    void thrownEntityNotFound_whenPutProvider_shouldReturn400Response() throws Exception {
        final Long randomId = randomLong();
        final ProviderRequest request = ProviderRequestFactory.providerRequest();

        final EntityNotFoundException exception = new EntityNotFoundException(
                ProviderJpaEntity.TABLE_NAME,
                ProviderJpaEntity.ID_COL,
                randomId
        );

        when(providerService.update(randomId, request))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .put(PUT_PROVIDER_URL, randomId)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    void givenProviderRequest_whenDelProvider_shouldReturn204Response() throws Exception {
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(DEL_PROVIDER_URL, randomLong())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Check response http status code
        result.andExpect(status().isNoContent());
    }

    @Test
    void thrownEntityNotFound_whenDelProvider_shouldReturn400Response() throws Exception {
        final Long randomId = randomLong();

        final EntityNotFoundException exception = new EntityNotFoundException(
                ProviderJpaEntity.TABLE_NAME,
                ProviderJpaEntity.ID_COL,
                randomId
        );

        when(providerService.delete(randomId))
                .thenThrow(exception);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(DEL_PROVIDER_URL, randomId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }
}
