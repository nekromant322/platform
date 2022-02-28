package com.override.command;

import com.override.services.RegisterService;
import dtos.JoinRequestStatusDTO;
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

        RegisterStudentRequestDTO requestDTO = RegisterStudentRequestDTO.builder()
                .chatId(chat.getId().toString())
                .telegramUserName(user.getUserName())
                .build();

        JoinRequestStatusDTO status = registerService.registerRequest(requestDTO);
        message.setText(status.getMessage());

        execute(absSender, message, user);
    }
}