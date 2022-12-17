package com.override.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheManagerSettingsGenerationService {

    private final String SPEC_AS_STRING = "maximumSize=%s,expireAfterAccess=%s";

    @Value("${cacheManager.restoreTimeout}")
    private String timeToExpireRestoreCache;

    @Value("${cacheManager.verificationTimeout}")
    private String timeToExpireVerificationCache;

    @Value("${cacheManager.size}")
    private long sizeOfCache;

    public String getVerificationCacheManagerSpecification() {
        return String.format(SPEC_AS_STRING, sizeOfCache, timeToExpireVerificationCache);
    }

    public String getRestoreCacheManagerSpecification() {
        return String.format(SPEC_AS_STRING, sizeOfCache, timeToExpireRestoreCache);
    }
}
