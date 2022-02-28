package com.override.controller;

import com.override.models.JoinRequest;
import com.override.service.JoinRequestService;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterStudentRequestDTO;
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
    public JoinRequestStatusDTO saveJoinRequest(@RequestBody RegisterStudentRequestDTO requestDTO) {
        return requestService.saveRequest(requestDTO);
    }

    @GetMapping
    public List<JoinRequest> getAllJoinRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping
    @RequestMapping("/accept")
    public void acceptJoinRequest(@RequestBody Long id) {
        requestService.responseForJoinRequest(true, id);
    }

    @PostMapping
    @RequestMapping("/decline")
    public void declineJoinRequest(@RequestBody Long id) {
        requestService.responseForJoinRequest(false, id);
    }
}