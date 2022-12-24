package com.override.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheManagerConfig {

    private final String SPEC_AS_STRING = "maximumSize=%s,expireAfterAccess=%s";

    @Value("${cacheManager.timeout}")
    private String timeToExpireCache;

    @Value("${cacheManager.size}")
    private long sizeOfCache;

    @Bean
    public CacheManager getCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheSpecification(String.format(SPEC_AS_STRING, sizeOfCache, timeToExpireCache));
        return cacheManager;
    }
}
