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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinRequestService {

    private final JoinRequestRepository requestRepository;
    private final TelegramBotFeign telegramBotFeign;
    private final JoinRequestMapper joinRequestMapper;
    private final StudentAccountService accountService;
    private final StudentAccountMapper accountMapper;

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
        log.info("Новый запрос от {} в чате № {}", request.getTelegramUserName(), request.getChatId());
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
            student = accountMapper.entityToDto(accountService.generateAccount(request.getNickName(), request.getChatId()));
            responseDTO = ResponseJoinRequestDTO.builder().accountDTO(student).status(RequestStatus.APPROVED).build();
            log.info("Запрос от {} в чате № {} разрешен", request.getNickName(), request.getChatId());
        } else {
            student = StudentAccountDTO.builder().telegramChatId(request.getChatId()).build();
            responseDTO = ResponseJoinRequestDTO.builder().status(RequestStatus.DECLINED).accountDTO(student).build();
            log.info("Запрос от {} в чате № {} отклонен", request.getNickName(), request.getChatId());
        }
        requestRepository.delete(request);
        telegramBotFeign.responseForRequest(responseDTO);
    }
}