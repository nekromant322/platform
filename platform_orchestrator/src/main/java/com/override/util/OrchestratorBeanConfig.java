package com.override.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dtos.HelpMeTaskTextAndCodeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OrchestratorBeanConfig {

    @Value("${cache.timeout}")
    private long timeToExpireCache;

    @Bean
    Cache<Integer, HelpMeTaskTextAndCodeDTO> getCacheBean() {
        Cache<Integer, HelpMeTaskTextAndCodeDTO> resultCache = Caffeine.newBuilder()
                .expireAfterWrite(timeToExpireCache, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        return resultCache;
    }

}
