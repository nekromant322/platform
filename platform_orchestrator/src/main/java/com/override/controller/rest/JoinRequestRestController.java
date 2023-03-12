package com.override.controller.rest;

import com.override.model.JoinRequest;
import com.override.service.JoinRequestService;
import dto.JoinRequestStatusDTO;
import dto.RegisterUserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class JoinRequestRestController {

    @Autowired
    private JoinRequestService requestService;

    @PostMapping("/join/request")
    @Operation(summary = "Сохраняет запрос на регистрацию в БД, если пользователь еще не " +
            "зарегистрирован, или не отправлял запрос ранее")
    public JoinRequestStatusDTO saveJoinRequest(@RequestBody RegisterUserRequestDTO requestDTO) {
        return requestService.saveRequest(requestDTO);
    }

    @GetMapping("/join/request")
    @Operation(summary = "Возвращает весь список запросов на регистрацию из БД")
    public List<JoinRequest> getAllJoinRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping("/join/request/accept/{id}")
    @Operation(summary = "Админ принимает запрос на регистрацию. В телеграм пользователю отправляется " +
            "сообщение о принятии запроса, а так же логин и пароль для входа на платформу. Запрос удаляется из БД")
    public void acceptJoinRequest(@PathVariable Long id) {
        requestService.responseForJoinRequest(true, id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/join/request/decline/{id}")
    @Operation(summary = "Админ не принимает запрос на регистрацию. В телеграм пользователю отправляется " +
            "сообщение об отклонении запроса. Запрос удаляется из БД")
    public void declineJoinRequest(@PathVariable Long id) {
        requestService.responseForJoinRequest(false, id);
    }
}
