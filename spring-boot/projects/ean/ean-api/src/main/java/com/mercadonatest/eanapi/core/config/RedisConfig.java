package com.mercadonatest.eanapi.core.config;

import com.mercadonatest.eanapi.EanApiApplication;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@Profile({EanApiApplication.NON_TEST_PROFILE})
@EnableCaching
public class RedisConfig {

    public static final String CACHE_GET_PROVIDER_NAME = "getProviderCache";
    public static final String CACHE_GET_DESTINY_NAME = "getDestinyCache";
    public static final String CACHE_GET_PRODUCT_NAME = "getProductCache";
    public static final String CACHE_GET_EAN_NAME = "getEanCache";

    @Bean
    @Profile({EanApiApplication.NON_TEST_PROFILE})
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()
                        )
                );
    }

    @Bean
    @Profile({EanApiApplication.NON_TEST_PROFILE})
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration(
                        CACHE_GET_PROVIDER_NAME,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                                Duration.ofMinutes(1)
                        )
                )
                .withCacheConfiguration(
                        CACHE_GET_DESTINY_NAME,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                                Duration.ofMinutes(1)
                        )
                )
                .withCacheConfiguration(
                        CACHE_GET_PRODUCT_NAME,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                                Duration.ofMinutes(1)
                        )
                );
    }
}
