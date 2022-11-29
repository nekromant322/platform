package com.override.service;

import com.override.feign.StepikCodeExecutorFeign;
import dto.CodeTryDTO;
import dto.StepikTokenDTO;
import dto.TestResultDTO;
import enums.CodeExecutionStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Этот класс используется для проверки кода при помощи StepikAPI
 * Константа REQUESTS_JSON необходима для отправки запроса к StepikAPI, переменные attempt и step устанавливаются методом getRequest
 * Метод getStepikToken запрашивает токен у StepikAPI по логину и паролю клиента - authorization
 * Метод putStepikTokenInCache кладет токен в Кэш
 * Метод getRequest Добавляет в константу REQUESTS_JSON две необходимые переменные: code и attempt, после возвращает строку запроса.
 * Метод runStepikCode выполняет проверку кода и возвращает модель с результатом проверки (output)
 */
@Service
public class StepikService {

    private final String REQUESTS_JSON = "{\"submission\":{\"eta\":null,\"has_session\":false,\"hint\":null,\"reply\":{\"code\":%s,\"language\":\"java8\"},\"reply_url\":null,\"score\":null,\"session_id\":null,\"status\":null,\"time\":\"2022-11-22T17:07:38.090Z\",\"attempt\":%s,\"session\":null}}";

    @Value("${stepik.authorization}")
    private String authorization;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    public TestResultDTO runStepikCode(CodeTryDTO codeTryDTO) {
        String token = getStepikToken();
        stepikCodeExecutorFeign.execute(token, getRequest(codeTryDTO));
        String result = stepikCodeExecutorFeign.getResult(codeTryDTO.getStep(), token);
        while (result.contains("evaluation")) {
            result = stepikCodeExecutorFeign.getResult(codeTryDTO.getStep(), token);
        }
        TestResultDTO testResultDTO = TestResultDTO.builder().output(result).build();
        testResultDTO.setCodeExecutionStatus(CodeExecutionStatus.OK);

        return testResultDTO;
    }

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
    public String getRequest(CodeTryDTO codeTryDTO) {
        System.out.println(String.format(REQUESTS_JSON, codeTryDTO.getStudentsCode(), codeTryDTO.getAttempt()));
        return String.format(REQUESTS_JSON, codeTryDTO.getStudentsCode(), codeTryDTO.getAttempt());
    }
}
