package com.override.feigns;

import dtos.RegisterStudentRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orchestrator")
public interface OrchestratorFeign {

    @PostMapping("/join/request")
    void saveJoinRequest(@RequestBody RegisterStudentRequestDTO requestDTO);
}