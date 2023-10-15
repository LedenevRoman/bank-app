package com.training.rledenev.services.bot;

import com.training.rledenev.services.UpdateHandlerService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final UpdateHandlerService updateHandlerService;

    public MyTelegramBot(BotConfig botConfig, UpdateHandlerService updateHandlerService) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.updateHandlerService = updateHandlerService;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = updateHandlerService.handleUpdate(update);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
