package com.override.feign;

import dto.CodeTryDTO;
import dto.TestResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "code-executor")
public interface CodeExecutorFeign {

    @PostMapping("/execute")
    TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO);
}
