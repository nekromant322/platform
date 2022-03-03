package com.override.controller.rest;

import com.override.models.JoinRequest;
import com.override.service.JoinRequestService;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/join/request")
public class JoinRequestRestController {

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