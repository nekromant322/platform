package com.override.feign;

import dto.JoinRequestStatusDTO;
import dto.RegisterUserRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orchestrator")
public interface OrchestratorFeign {
    @PostMapping("/join/request")
    JoinRequestStatusDTO saveJoinRequest(@RequestBody RegisterUserRequestDTO requestDTO);
}