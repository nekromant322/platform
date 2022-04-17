package com.override.controller;

import com.override.service.MessagesService;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/communications")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    /**
     * Метод контроллера который необходимо использовать в сервисах, если нужно отправить сообщение пользователю
     * по средствам какой-либо коммуникации
     * @param login   логин пользователя, которому будет отправлено сообщение
     * @param message текст сообщения
     * @param type  тип коммуникации с пользователем, например: TELEGRAM, VK, EMAIL
     * @return результат успешной работы или ошибку связанную с отправкой сообщения
     */
    @PostMapping
    ResponseEntity<HttpStatus> sendMessage(
            @RequestParam("login") String login,
            @RequestParam("message") String message,
            @RequestParam("type") Communication... type) {
        return messagesService.sendMessage(login, message, type);
    }
}
