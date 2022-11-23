package com.override.service;

import com.override.feign.StepikCodeExecutorFeign;
import dto.StepikTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class StepikService {

    @Value("${orchestrator.authorization}")
    private String AUTHORIZATION;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    public void putStepikTokenInCache(StepikTokenDTO stepikTokenDTO) {
        Cache data = cacheManager.getCache("stepikToken");
        String token = stepikTokenDTO.getAccess_token();
        data.put("token", "Bearer " + token);
    }

    public String getStepikToken() {
        Cache data = cacheManager.getCache("stepikToken");
        if (data.get("token") == null) {
            StepikTokenDTO stepikTokenDTO = stepikCodeExecutorFeign.getToken(AUTHORIZATION);
            putStepikTokenInCache(stepikTokenDTO);
        }
        Cache.ValueWrapper cacheCode = data.get("token");
        String token = (String) cacheCode.get();
        return token;
    }
}
