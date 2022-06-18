package com.override.services;

import com.override.feign.OrchestratorFeign;
import dto.JoinRequestStatusDTO;
import dto.RegisterUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private OrchestratorFeign orchestratorFeign;

    public JoinRequestStatusDTO registerRequest(RegisterUserRequestDTO requestDTO) {
        return orchestratorFeign.saveJoinRequest(requestDTO);
    }
}