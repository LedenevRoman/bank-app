package com.training.rledenev.bankapp.services.bot.impl;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public final class BotUtils {
    static final String START = "/start";
    static final String WELCOME_MESSAGE = "Welcome to banking application!";
    static final String EXIT = "Exit";
    static final String REGISTER_USER = "Register";
    static final String LOG_IN = "Log in";
    static final String UNKNOWN_INPUT_MESSAGE = "Sorry, I don't know how to handle such command yet :(";
    static final String ENTER_FIRST_NAME = "Please, enter your first name:";
    static final String ENTER_LAST_NAME = "Please, enter your last name:";
    static final String ENTER_PHONE = "Please, enter your phone number:";
    static final String ENTER_ADDRESS = "Please, enter your address:";
    static final String ENTER_EMAIL = "Please, enter your email:";
    static final String ENTER_PASSWORD = "Please, enter your password:";
    static final String INCORRECT_NAME = "The name must contain only letters of the English alphabet."
            + "\n"
            + "Please, enter correct name:";
    static final String INCORRECT_PHONE = "The phone number must starts with '+', and contain only numbers and hyphens."
            + "\n"
            + "Please, enter correct phone number:";
    static final String INCORRECT_ADDRESS = "Address is incorrect."
            + "\n"
            + "Please, enter correct address:";
    static final String INCORRECT_EMAIL = "Email is incorrect."
            + "\n"
            + "Please, enter correct email:";
    static final String INCORRECT_PASSWORD = "The password is incorrect. Password is required to contain only English "
            + "alphabet characters at least one uppercase and one lowercase, also one digit and one special character."
            + "\n"
            + "Please, enter correct password:";
    static final String REGISTRATION_COMPLETED = "Registration is completed";
    static final String AUTHENTICATION_FAILED = "Email or password is incorrect";
    static final String AUTHENTICATION_COMPLETED = "Good afternoon %s %s!";
    static final String SESSION_CLOSED = "Session was expired, please log in";


    private BotUtils() {
    }

    public static SendMessage createSendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);
        return sendMessage;
    }

    public static SendMessage addButtonsToMessage(SendMessage sendMessage, List<String> buttons) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        for (String button : buttons) {
            row.add(new KeyboardButton(button));
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
