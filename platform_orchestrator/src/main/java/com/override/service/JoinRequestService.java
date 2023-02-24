package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.mapper.JoinRequestMapper;
import com.override.mapper.PlatformUserMapper;
import com.override.model.JoinRequest;
import com.override.repository.JoinRequestRepository;
import dto.*;
import enums.CommunicationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JoinRequestService {

    private final String DECLINE_MESSAGE = "Ваш запрос не был одобрен, повторите попытку";

    private final String ACCEPT_MESSAGE = "login: %s\npassword: %s";

    @Autowired
    private JoinRequestRepository requestRepository;

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
        RecipientDTO recipientDTO = RecipientDTO.builder().login(request.getNickName()).telegramId(request.getChatId()).email("").build();
        notificatorFeign.saveRecipient(recipientDTO);
        if (approve) {
            student = accountMapper.entityToDto(accountService.generateAccount(request.getNickName()));
            log.info("Запрос от {} в чате № {} разрешен", request.getNickName(), request.getChatId());
            notificatorFeign.sendMessage(student.getLogin(), String.format(ACCEPT_MESSAGE, student.getLogin(), student.getPassword()), CommunicationType.TELEGRAM);
        } else {
            student = PlatformUserDTO.builder().login(request.getNickName()).build();
            notificatorFeign.deleteRecipient(recipientDTO);
            log.info("Запрос от {} в чате № {} отклонен", request.getNickName(), request.getChatId());
            notificatorFeign.sendMessage(student.getLogin(), DECLINE_MESSAGE, CommunicationType.TELEGRAM);
        }
        requestRepository.delete(request);
    }
}