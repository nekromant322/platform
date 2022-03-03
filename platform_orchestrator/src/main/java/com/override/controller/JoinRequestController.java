package com.override.controller;

import com.override.models.JoinRequest;
import com.override.service.JoinRequestService;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/join/request")
public class JoinRequestController {

    @Autowired
    private JoinRequestService requestService;

    @PostMapping
    public JoinRequestStatusDTO saveJoinRequest(@RequestBody RegisterUserRequestDTO requestDTO) {
        return requestService.saveRequest(requestDTO);
    }

    @GetMapping
    public List<JoinRequest> getAllJoinRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping("/accept")
    public void acceptJoinRequest(@RequestParam Long id) {
        requestService.responseForJoinRequest(true, id);
    }

    @PostMapping("/decline")
    public void declineJoinRequest(@RequestParam Long id) {
        requestService.responseForJoinRequest(false, id);
    }
}
