package com.override;

import com.override.command.PlatformBotCommand;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Slf4j
@Component
public class PlatformBot extends TelegramLongPollingCommandBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;


    @Autowired
    public PlatformBot(List<PlatformBotCommand> allCommands) {
        super();
        allCommands.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {

        } else if (update.hasCallbackQuery()) {

        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    @SneakyThrows
    public void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        //убирает превьюшки ссылок
        message.disableWebPagePreview();
        message.setText(text);
        message.setChatId(chatId);
        try {
            execute(message);
        } catch (TelegramApiException ignored) {
            log.error("fix long to int telegram-bot exception");
        }
    }
}
