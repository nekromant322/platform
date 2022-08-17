package com.override.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dto.HelpMeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${cache.timeout}")
    private long timeToExpireCache;

    @Value("${cache.size}")
    private long sizeOfCache;

    @Bean
    public Cache<Integer, HelpMeDTO> getCacheBean() {
        return Caffeine.newBuilder()
                .expireAfterWrite(timeToExpireCache, TimeUnit.MINUTES)
                .maximumSize(sizeOfCache)
                .build();
    }
}
