package com.override.services;

import com.override.feigns.OrchestratorFeign;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterStudentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private OrchestratorFeign orchestratorFeign;

    public JoinRequestStatusDTO registerRequest(RegisterStudentRequestDTO requestDTO) {
        return orchestratorFeign.saveJoinRequest(requestDTO);
    }
}