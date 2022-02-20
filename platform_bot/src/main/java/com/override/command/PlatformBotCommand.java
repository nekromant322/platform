package com.override.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class PlatformBotCommand extends org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand {

    public PlatformBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender sender, SendMessage message, User user) {
        log.info(this.getDescription() + " , пользователь - " + user.getUserName());
        log.info("Ответ бота: \n" + message.getText() + "\n");
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}