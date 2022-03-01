package com.override.services;

import com.override.feigns.OrchestratorFeign;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private OrchestratorFeign orchestratorFeign;

    public JoinRequestStatusDTO registerRequest(RegisterUserRequestDTO requestDTO) {
        return orchestratorFeign.saveJoinRequest(requestDTO);
    }
}