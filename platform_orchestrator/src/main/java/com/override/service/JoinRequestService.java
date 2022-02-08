package com.override.service;


import dtos.RegisterStudentRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class JoinRequestService {

    public void saveRequest(RegisterStudentRequestDTO request) {
        // TODO что делать с реквестом?
        System.out.println(request);
    }
}