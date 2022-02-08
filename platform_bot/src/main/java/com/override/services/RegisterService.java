package com.override.services;

import com.override.feigns.OrchestratorFeign;
import dtos.RegisterStudentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final OrchestratorFeign orchestratorFeign;

    public void registerRequest(RegisterStudentRequestDTO requestDTO) {
        orchestratorFeign.saveJoinRequest(requestDTO);
    }
}