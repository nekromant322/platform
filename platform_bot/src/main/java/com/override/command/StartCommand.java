package com.override.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class StartCommand extends PlatformBotCommand {
    public StartCommand() {
        super("/start", "Начать работу с ботом");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        message.setText("Для регистрации в платформа нажмите /register");

        execute(absSender, message, user);
    }
}
