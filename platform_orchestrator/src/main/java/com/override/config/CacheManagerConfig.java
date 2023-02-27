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

    @Value("${cacheManager.interviews.size}")
    private long sizeOfInterviewDataCache;

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
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("interviewData", "interviewDataById");
        cacheManager.setCacheSpecification(String.format(SPEC_AS_STRING, sizeOfInterviewDataCache, timeToExpireInterviewDataCache));
        return cacheManager;
    }
}
