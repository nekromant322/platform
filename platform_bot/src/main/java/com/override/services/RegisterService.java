package com.override.services;

import com.override.feigns.OrchestratorFeign;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final OrchestratorFeign orchestratorFeign;

    public JoinRequestStatusDTO registerRequest(RegisterUserRequestDTO requestDTO) {
        return orchestratorFeign.saveJoinRequest(requestDTO);
    }
}