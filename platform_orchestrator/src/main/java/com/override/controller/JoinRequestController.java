package com.override.controller;

import com.override.service.JoinRequestService;
import dtos.RegisterStudentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join/request")
@RequiredArgsConstructor
public class JoinRequestController {

    private final JoinRequestService requestService;

    @PostMapping
    public void saveJoinRequest(@RequestBody RegisterStudentRequestDTO requestDTO) {
        requestService.saveRequest(requestDTO);
    }
}