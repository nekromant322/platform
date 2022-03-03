package com.override.controller.rest;

import com.override.models.JoinRequest;
import com.override.service.JoinRequestService;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class JoinRequestRestController {

    @Autowired
    private JoinRequestService requestService;

    @PostMapping("/join/request")
    public JoinRequestStatusDTO saveJoinRequest(@RequestBody RegisterUserRequestDTO requestDTO) {
        return requestService.saveRequest(requestDTO);
    }

    @GetMapping("/admin/join/request")
    public List<JoinRequest> getAllJoinRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping("/admin/join/request/accept/{id}")
    public void acceptJoinRequest(@PathVariable Long id) {
        requestService.responseForJoinRequest(true, id);
    }

    @PostMapping("/admin/join/request/decline/{id}")
    public void declineJoinRequest(@PathVariable Long id) {
        requestService.responseForJoinRequest(false, id);
    }
}
