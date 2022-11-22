package com.override.service;

import com.override.feign.StepikCodeExecutorFeign;
import dto.StepikTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class StepikService {

    private final String AUTHORIZATION = "Basic a3l6WnQyME5oVTJYUngyem1oRWNQN2w0cVNRdHVVTzRTY3VCRElkRTpaT1hSamJFSm1CdnVTQnhOYjQwZHFKSE9hZTltbzFvTEJDbWlIYUZPcnl2b2xQM1A1bGZDMExhYUlCdUNIbzhTTUEwdnliRnJDVmo4U2pJZEVmYVFtYjFPbjhzZkxrT0JPVWJITmcxQWxkSVRDMkNmMnQyVHM2ak4wcXFrVElwRg==";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    public void putStepikTokenInCache(StepikTokenDTO stepikTokenDTO){
        Cache data = cacheManager.getCache("stepikToken");
        String token = stepikTokenDTO.getAccess_token();
        data.put("token","Bearer " + token );
    }

    public String getStepikToken(){
        Cache data = cacheManager.getCache("stepikToken");
        if (data.get("token") == null){
            StepikTokenDTO stepikTokenDTO = stepikCodeExecutorFeign.getToken(AUTHORIZATION);
            putStepikTokenInCache(stepikTokenDTO);
        }
        Cache.ValueWrapper cacheCode = data.get("token");
        String token = (String) cacheCode.get();
        return token;
    }
}
