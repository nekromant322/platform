package com.override.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class PlatformBotCommand extends org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand {

    public PlatformBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender sender, SendMessage message, User user) {
        System.out.println(this.getDescription() + " , пользователь - " + user.getUserName());
        System.out.println("output: \n" + message.getText() + "\n");
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}