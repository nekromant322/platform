package com.override.feign;

import dto.StepikTokenDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "stepik-code-executor", url="https://stepik.org")
@Headers({"Authorization: Bearer AoWQTbnrb3Z8YN5D1jpbQKkKU8Pdj3",
        "Cookie: csrftoken=JhKW626iE7IWaQdZxE5JH3lFJmA3gMYmEu0kaLP8fAxw5mpJjZ3dKe8pmCsN36bP; sessionid=zohpv1qh2pybdqcty36a58w47tspi841",
        "Content-Type: application/json",
        "Content-Length: <calculated when request is sent>",
        "Host: <calculated when request is sent>"})
public interface StepikCodeExecutorFeign {

    @PostMapping(value = "/api/submissions",consumes = "application/json", produces = "application/json")
     void execute(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody String json);

    @GetMapping(value = "/api/submissions?limit=1&order=desc&step=57792",consumes = "application/json", produces = "application/json")
    String getResult (@RequestHeader(value = "Authorization", required = true) String token);

    @PostMapping(value = "/oauth2/token/?grant_type=client_credentials",consumes = "application/json", produces = "application/json")
    StepikTokenDTO getToken(@RequestHeader(value = "Authorization", required = true) String auth);
}
