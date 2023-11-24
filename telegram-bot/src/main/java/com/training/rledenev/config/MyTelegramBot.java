package com.training.rledenev.config;

import com.training.rledenev.service.UpdateHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
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
            log.error(e.getMessage());
        }
    }
}
