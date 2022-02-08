package com.override.command;

import com.override.services.RegisterService;
import dtos.RegisterStudentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class RegisterCommand extends PlatformBotCommand {

    @Autowired
    private RegisterService registerService;


    public RegisterCommand() {
        super("/register", "Отправить запрос на регистрацию");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        message.setText("Ваш запрос на регистрацию в платформе создан, ожидайте подтверждения");

        RegisterStudentRequestDTO requestDTO = RegisterStudentRequestDTO.builder()
                .chatId(chat.getId().toString())
                .name(user.getUserName())
                .build();

        registerService.registerRequest(requestDTO);

        execute(absSender, message, user);
    }
}