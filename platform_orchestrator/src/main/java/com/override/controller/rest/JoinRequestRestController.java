package com.override.controller.rest;

import com.override.model.JoinRequest;
import com.override.service.JoinRequestService;
import dto.JoinRequestStatusDTO;
import dto.RegisterUserRequestDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class JoinRequestRestController {

    @Autowired
    private JoinRequestService requestService;

    @PostMapping("/join/request")
    @ApiOperation(value = "Сохраняет запрос на регистрацию в БД, если пользователь еще не " +
            "зарегистрирован, или не отправлял запрос ранее")
    public JoinRequestStatusDTO saveJoinRequest(@RequestBody RegisterUserRequestDTO requestDTO) {
        return requestService.saveRequest(requestDTO);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/join/request")
    @ApiOperation(value = "Возвращает весь список запросов на регистрацию из БД")
    public List<JoinRequest> getAllJoinRequests() {
        return requestService.getAllRequests();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/join/request/accept/{id}")
    @ApiOperation(value = "Админ принимает запрос на регистрацию. В телеграм пользователю отправляется " +
            "сообщение о принятии запроса, а так же логин и пароль для входа на платформу. Запрос удаляется из БД")
    public void acceptJoinRequest(@PathVariable Long id) {
        requestService.responseForJoinRequest(true, id);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "Админ не принимает запрос на регистрацию. В телеграм пользователю отправляется " +
            "сообщение об отклонении запроса. Запрос удаляется из БД")
    @PostMapping("/join/request/decline/{id}")
    public void declineJoinRequest(@PathVariable Long id) {
        requestService.responseForJoinRequest(false, id);
    }
}
