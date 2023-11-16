package com.mercadonatest.eanapi.modules.providers.infrastructure.repositories;

import com.mercadonatest.eanapi.EanApiApplication;
import com.mercadonatest.eanapi.ReplaceUnderscoresAndCamelCase;
import com.mercadonatest.eanapi.annotations.EnabledIfDocker;
import com.mercadonatest.eanapi.containers.MockPostgreSqlContainer;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import com.mercadonatest.eanapi.modules.provider.domain.repositories.ProviderRepository;
import com.mercadonatest.eanapi.modules.providers.domain.models.entities.ProviderJpaEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static com.mercadonatest.eanapi.TestUtils.randomInt;
import static com.mercadonatest.eanapi.TestUtils.randomLong;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@Testcontainers
@EnabledIfDocker
@ActiveProfiles(EanApiApplication.INT_TEST_PROFILE)
class ProviderRepositoryTests {
    @Container
    private static final PostgreSQLContainer<?> postgreSqlContainer = MockPostgreSqlContainer.getInstance();

    @DynamicPropertySource
    static void registerSettings(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.datasource.url",
                postgreSqlContainer::getJdbcUrl
        );
    }

    @Autowired
    private ProviderRepository providerRepository;

    private ProviderJpaEntity testProvider;

    private final List<ProviderJpaEntity> testProviderList = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        testProvider = providerRepository.save(
                ProviderJpaEntityFactory.providerJpaEntity());
        testProviderList.add(testProvider);
        for (int i = 0; i < randomInt(20, 40); i++) {
            testProviderList.add(
                    providerRepository.save(
                            ProviderJpaEntityFactory
                                    .providerJpaEntity()
                    )
            );
        }
    }

    @Test
    @Transactional
    void storedProviderEntity_whenExistsById_shouldReturnTrue() {
        final boolean exists = providerRepository.existsById(
                testProvider.getId()
        );

        assertTrue(exists);
    }

    @Test
    @Transactional
    void nonexistentProviderEntity_whenExistsById_shouldReturnFalse() {
        final boolean exists = providerRepository.existsById(
                randomLong()
        );

        assertFalse(exists);
    }

    @Test
    @Transactional
    void givenProviderEntity_whenSave_shouldReturnStoredEntity() {
        final ProviderJpaEntity entity = ProviderJpaEntityFactory
                .providerJpaEntityBuilder()
                .id(null)
                .build();

        final ProviderJpaEntity storedEntity = providerRepository.save(
                entity
        );

        assertNotNull(storedEntity);

        entity.setId(storedEntity.getId());

        assertThat(storedEntity)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }
}
