package com.override.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CacheManagerConfig {

    private final String SPEC_AS_STRING = "maximumSize=%s,expireAfterAccess=%s";

    @Value("${cacheManager.restoreTimeout}")
    private String timeToExpireRestoreCache;

    @Value("${cacheManager.verificationTimeout}")
    private String timeToExpireVerificationCache;

    @Value("${cacheManager.sizeOfRestoreCache}")
    private long sizeOfRestoreCache;

    @Value("${cacheManager.sizeOfVerificationCache}")
    private long sizeOfVerificationCache;

    @Primary
    @Bean
    public CacheManager getRestoreCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheSpecification(String.format(SPEC_AS_STRING, sizeOfRestoreCache, timeToExpireRestoreCache));
        return cacheManager;
    }

    @Bean
    public CacheManager getVerificationCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheSpecification(String.format(SPEC_AS_STRING, sizeOfVerificationCache, timeToExpireVerificationCache));
        return cacheManager;
    }
}
