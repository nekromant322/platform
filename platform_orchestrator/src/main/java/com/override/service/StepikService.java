package com.override.service;

import com.override.feign.StepikCodeExecutorFeign;
import dto.StepikTokenDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class StepikService {

    @Value("${stepik.authorization}")
    private String AUTHORIZATION;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    @ApiOperation(value = "Кладет токен в Кэш")
    public void putStepikTokenInCache(StepikTokenDTO stepikTokenDTO) {
        Cache data = cacheManager.getCache("stepikToken");
        String token = stepikTokenDTO.getAccess_token();
        data.put("token", "Bearer " + token);
    }

    @ApiOperation(value = "Запрашивает токен у StepikAPI по логину и паролю клиента - AUTHORIZATION")
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
