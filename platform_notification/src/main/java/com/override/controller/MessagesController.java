package com.override.controller;

import com.override.service.MessageService;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communications")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    /**
     * Метод контроллера который необходимо использовать в сервисах, если нужно отправить сообщение пользователю
     * по средствам какой-либо коммуникации
     * @param login   логин пользователя, которому будет отправлено сообщение
     * @param message текст сообщения
     * @param type  тип коммуникации с пользователем, например: TELEGRAM, VK, EMAIL
     */
    @PostMapping
    void sendMessage(
            @RequestParam("login") String login,
            @RequestParam("message") String message,
            @RequestParam("type") Communication... type) {
        messageService.sendMessage(login, message, type);
    }

    @GetMapping("/types")
    public List<Communication> CheckNotificationMethods(@RequestParam("login")String login){
        return messageService.checkNotificationMethods(login);
    }
}
