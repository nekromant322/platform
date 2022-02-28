package com.override.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dtos.HelpMeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OrchestratorBeanConfig {

    @Value("${cache.timeout}")
    private long timeToExpireCache;

    @Value("${cache.size}")
    private long sizeOfCache;

    @Bean
    Cache<Integer, HelpMeDTO> getCacheBean() {
        Cache<Integer, HelpMeDTO> resultCache = Caffeine.newBuilder()
                .expireAfterWrite(timeToExpireCache, TimeUnit.MINUTES)
                .maximumSize(sizeOfCache)
                .build();
        return resultCache;
    }

}
