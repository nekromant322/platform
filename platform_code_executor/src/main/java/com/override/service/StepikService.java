package com.override.service;

import com.override.feign.StepikCodeExecutorFeign;
import dto.StepikCodeTryDTO;
import dto.StepikTokenDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class StepikService {

    private final String REQUESTS_JSON = "{\"submission\":{\"eta\":null,\"has_session\":false,\"hint\":null,\"reply\":{\"code\":%s,\"language\":\"java8\"},\"reply_url\":null,\"score\":null,\"session_id\":null,\"status\":null,\"time\":\"2022-11-22T17:07:38.090Z\",\"attempt\":%s,\"session\":null}}";

    @Value("${stepik.authorization}")
    private String authorization;

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
            StepikTokenDTO stepikTokenDTO = stepikCodeExecutorFeign.getToken(authorization);
            putStepikTokenInCache(stepikTokenDTO);
        }
        Cache.ValueWrapper cacheCode = data.get("token");
        String token = (String) cacheCode.get();
        return token;
    }

    @ApiOperation("Добавляет в константу REQUESTS_JSON две необходимые переменные: code и attempt, после возвращает строку запроса.")
    public String getRequest(StepikCodeTryDTO stepikCodeTryDTO) {
        return String.format(REQUESTS_JSON, stepikCodeTryDTO.getCode(), stepikCodeTryDTO.getAttempt());
    }
}
