package com.override.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CacheManagerConfig {

    private final String SPEC_AS_STRING = "maximumSize=%s,expireAfterAccess=%s";
    private final String SPEC_EXPIRE_AFTER_ACCESS_AS_STRING = "expireAfterAccess=%s";

    @Value("${cacheManager.restore.timeout}")
    private String timeToExpireRestoreCache;

    @Value("${cacheManager.verification.timeout}")
    private String timeToExpireVerificationCache;

    @Value("${cacheManager.interviews.timeout}")
    private String timeToExpireInterviewDataCache;

    @Value("${cacheManager.restore.size}")
    private long sizeOfRestoreCache;

    @Value("${cacheManager.verification.size}")
    private long sizeOfVerificationCache;

    @Primary
    @Bean
    public CacheManager getCacheManager() {
        return new CaffeineCacheManager();
    }

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

    @Bean
    public CacheManager getInterviewDataCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("interviewData");
        cacheManager.setCacheSpecification(String.format(SPEC_EXPIRE_AFTER_ACCESS_AS_STRING, timeToExpireInterviewDataCache));
        return cacheManager;
    }
}