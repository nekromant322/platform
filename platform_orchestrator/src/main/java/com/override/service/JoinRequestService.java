package com.override.service;


import com.override.feigns.TelegramBotFeign;
import com.override.mappers.JoinRequestMapper;
import com.override.mappers.StudentAccountMapper;
import com.override.models.JoinRequest;
import com.override.repositories.JoinRequestRepository;
import dtos.JoinRequestStatusDTO;
import dtos.RegisterStudentRequestDTO;
import dtos.ResponseJoinRequestDTO;
import dtos.StudentAccountDTO;
import enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinRequestService {

    @Autowired
    private JoinRequestRepository requestRepository;

    @Autowired
    private TelegramBotFeign telegramBotFeign;

    @Autowired
    private JoinRequestMapper joinRequestMapper;

    @Autowired
    private StudentAccountService accountService;

    @Autowired
    private StudentAccountMapper accountMapper;

    public JoinRequestStatusDTO saveRequest(RegisterStudentRequestDTO request) {
        String message;
        if (requestRepository.findFirstByChatId(request.getChatId()) != null) {
            message = "В этом чате уже есть запрос на регистрацию";
        } else if (accountService.getAccountByChatId(request.getChatId()) != null) {
            message = "Вы уже зарегистрированы";
        } else {
            message = "Ваш запрос на регистрацию в платформе создан, ожидайте подтверждения";
            requestRepository.save(joinRequestMapper.dtoToEntity(request));
        }
        return new JoinRequestStatusDTO(message);
    }

    public List<JoinRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public void responseForJoinRequest(boolean approve, Long id) {
        JoinRequest request = requestRepository.findById(id).get();
        StudentAccountDTO student;
        ResponseJoinRequestDTO responseDTO;
        if (approve) {
            student = accountMapper.entityToDto(accountService.generateAccount(request));
            responseDTO = ResponseJoinRequestDTO.builder().accountDTO(student).status(RequestStatus.APPROVED).build();
        } else {
            student = StudentAccountDTO.builder().telegramChatId(request.getChatId()).build();
            responseDTO = ResponseJoinRequestDTO.builder().status(RequestStatus.DECLINED).accountDTO(student).build();
        }
        requestRepository.delete(request);
        telegramBotFeign.responseForRequest(responseDTO);
    }
}