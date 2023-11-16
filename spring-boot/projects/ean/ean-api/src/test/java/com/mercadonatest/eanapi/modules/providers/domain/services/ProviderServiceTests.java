package com.mercadonatest.eanapi.modules.providers.domain.services;

import com.mercadonatest.eanapi.ReplaceUnderscoresAndCamelCase;
import com.mercadonatest.eanapi.core.models.exceptions.EntityNotFoundException;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.models.mappers.ProviderMapper;
import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;
import com.mercadonatest.eanapi.modules.provider.domain.repositories.ProviderRepository;
import com.mercadonatest.eanapi.modules.provider.domain.services.ProviderServiceImpl;
import com.mercadonatest.eanapi.modules.providers.domain.models.entities.ProviderJpaEntityFactory;
import com.mercadonatest.eanapi.modules.providers.domain.models.requests.ProviderRequestFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mercadonatest.eanapi.TestUtils.randomLong;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@RunWith(MockitoJUnitRunner.class)
class ProviderServiceTests {
    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    private final ProviderMapper providerMapper = ProviderMapper.INSTANCE;

    @Test
    void givenValidProviderRequest_whenCreateProvider_shouldReturnProviderResponse() {
        final ProviderRequest request = ProviderRequestFactory.providerRequest();
        final ProviderJpaEntity entity = providerMapper.requestToEntity(request);

        final ArgumentCaptor<ProviderJpaEntity> providerArgumentCaptor = ArgumentCaptor.forClass(
                ProviderJpaEntity.class
        );

        providerService.create(request);

        verify(providerRepository)
                .save(providerArgumentCaptor.capture());

        final ProviderJpaEntity savedProvider = providerArgumentCaptor.getValue();

        assertThat(savedProvider)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void givenValidProviderId_whenGetProvider_shouldReturnValidProvider() {
        final ProviderJpaEntity providerEntity = ProviderJpaEntityFactory
                .providerJpaEntity();
        final ProviderResponse expectedResponse = providerMapper
                .entityToResponse(providerEntity);

        when(providerRepository.findById(providerEntity.getId()))
                .thenReturn(Optional.of(providerEntity));

        final ProviderResponse response = providerService.get(providerEntity.getId());

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void givenNotFoundProvider_whenGetProvider_shouldThrowEntityNotFoundException() {
        final Long providerId = randomLong();
        final EntityNotFoundException expectedException = new EntityNotFoundException(
                ProviderJpaEntity.TABLE_NAME,
                ProviderJpaEntity.ID_COL,
                providerId
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> providerService.get(providerId)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidProviderRequest_whenUpdateProvider_shouldReturnProviderResponse() {
        final ProviderRequest request = ProviderRequestFactory.providerRequest();
        final ProviderJpaEntity entity = providerMapper.requestToEntity(request);
        entity.setId(randomLong());

        when(providerRepository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        final ArgumentCaptor<ProviderJpaEntity> providerArgumentCaptor = ArgumentCaptor.forClass(
                ProviderJpaEntity.class
        );

        providerService.update(entity.getId(), request);

        verify(providerRepository)
                .save(providerArgumentCaptor.capture());

        final ProviderJpaEntity savedProvider = providerArgumentCaptor.getValue();

        assertThat(savedProvider)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void givenNotFoundProvider_whenUpdateProvider_shouldThrowEntityNotFoundException() {
        final long providerId = randomLong();
        final ProviderRequest request = ProviderRequestFactory.providerRequest();

        final EntityNotFoundException expectedException = new EntityNotFoundException(
                ProviderJpaEntity.TABLE_NAME,
                ProviderJpaEntity.ID_COL,
                providerId
        );

        final Exception exception = assertThrows(
                expectedException.getClass(),
                () -> providerService.update(providerId, request)

        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
