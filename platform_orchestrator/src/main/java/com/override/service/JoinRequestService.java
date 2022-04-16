package com.override.service;

import com.override.feigns.NotificatorFeign;
import com.override.feigns.TelegramBotFeign;
import com.override.mappers.JoinRequestMapper;
import com.override.mappers.PlatformUserMapper;
import com.override.models.JoinRequest;
import com.override.repositories.JoinRequestRepository;
import dtos.*;
import enums.RequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JoinRequestService {

    @Autowired
    private JoinRequestRepository requestRepository;

    @Autowired
    private TelegramBotFeign telegramBotFeign;

    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    private JoinRequestMapper joinRequestMapper;

    @Autowired
    private PlatformUserService accountService;

    @Autowired
    private PlatformUserMapper accountMapper;

    public JoinRequestStatusDTO saveRequest(RegisterUserRequestDTO request) {
        String message;
        if (requestRepository.findFirstByChatId(request.getChatId()) != null) {
            message = "В этом чате уже есть запрос на регистрацию";
        } else if (accountService.findPlatformUserByLogin(request.getTelegramUserName()) != null) {
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
        PlatformUserDTO student;
        ResponseJoinRequestDTO responseDTO;
        RecipientDTO recipientDTO;
        if (approve) {
            student = accountMapper.entityToDto(accountService.generateAccount(request.getNickName()));
            responseDTO = ResponseJoinRequestDTO.builder().accountDTO(student).status(RequestStatus.APPROVED).build();
            recipientDTO = RecipientDTO.builder().login(request.getNickName()).telegramId(request.getChatId()).build();
            notificatorFeign.saveRecipients(recipientDTO);
            log.info("Запрос от {} в чате № {} разрешен", request.getNickName(), request.getChatId());
        } else {
            student = PlatformUserDTO.builder().login(request.getNickName()).build();
            responseDTO = ResponseJoinRequestDTO.builder().status(RequestStatus.DECLINED).accountDTO(student).build();
            log.info("Запрос от {} в чате № {} отклонен", request.getNickName(), request.getChatId());
        }
        requestRepository.delete(request);
        telegramBotFeign.responseForRequest(responseDTO);
    }
}