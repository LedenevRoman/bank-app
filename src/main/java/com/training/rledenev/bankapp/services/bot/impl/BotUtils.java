package com.training.rledenev.bankapp.services.bot.impl;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public final class BotUtils {
    public static final String START = "/start";
    public static final String WELCOME_MESSAGE = "Welcome to banking application!";
    public static final String EXIT = "Exit";
    public static final String BACK = "Back";
    public static final String REGISTER_USER = "Register";
    public static final String LOG_IN = "Log in";
    public static final String UNKNOWN_INPUT_MESSAGE = "Sorry, I don't know how to handle such command yet :(";
    public static final String ENTER_FIRST_NAME = "Please, enter your first name:";
    public static final String ENTER_LAST_NAME = "Please, enter your last name:";
    public static final String ENTER_PHONE = "Please, enter your phone number:";
    public static final String ENTER_ADDRESS = "Please, enter your address:";
    public static final String ENTER_EMAIL = "Please, enter your email:";
    public static final String ENTER_PASSWORD = "Please, enter your password:";
    public static final String INCORRECT_NAME = "The name must contain only letters of the English alphabet." + "\n"
            + "Please, enter correct name:";
    public static final String INCORRECT_PHONE = "The phone number must starts with '+', and contain only numbers "
            + "and hyphens." + "\n"
            + "Please, enter correct phone number:";
    public static final String INCORRECT_ADDRESS = "Address is incorrect." + "\n" + "Please, enter correct address:";
    public static final String INCORRECT_EMAIL = "Email is incorrect." + "\n" + "Please, enter correct email:";
    public static final String INCORRECT_PASSWORD = "The password is incorrect. Password is required to contain "
            + "only English alphabet characters at least one uppercase and one lowercase, also one digit and "
            + "one special character." + "\n"
            + "Please, enter correct password:";
    public static final String REGISTRATION_COMPLETED = "Registration is completed";
    public static final String AUTHENTICATION_FAILED = "Email or password is incorrect." + "\n" + "Please, try again:";
    public static final String AUTHENTICATION_COMPLETED = "Good afternoon %s %s!" + "\n";
    public static final String SELECT_ACTION = "Select action:";
    public static final String SESSION_CLOSED = "Session was expired, please log in";
    public static final String PRODUCTS = "Products";
    public static final String PRODUCTS_LIST_MESSAGE = "Here is a list of available types of products:" + "\n";
    public static final String PRODUCTS_LIST_OF_TYPE = "Here is a list of products of type %s:" + "\n";
    public static final String PRODUCT_INFO = "Up to zł.%,d with an interest rate of %.2f%%, " +
            "for a period of %d months.";
    public static final String SELECT_PRODUCT = "Select your desired product:";
    public static final String SELECT_CURRENCY = "Select currency:";
    public static final String NOTE_ABOUT_CONVERT = "Note that the amount will be converted into PLN to compare" +
            " with the limit for your product.";
    public static final String ENTER_AMOUNT = "Enter the desired amount of money in your currency:";
    public static final String INCORRECT_NUMBER = "Number must contain only numbers and one dot." + "\n"
            + "Please, enter correct number:";
    public static final String AGREEMENT_DONE = "Done. You took the following product: " + "\n"
            + "%s" + "\n"
            + "on %s %s" + "\n"
            + "for a period of %d months." + "\n" + "\n"
            + "Please wait until the manager approves your application." + "\n" + "\n"
            + SELECT_ACTION;
    private static final int MAX_ROW_SIZE_IN_KEYBOARD = 4;


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
        List<KeyboardRow> keyboard = getKeyboardRows(buttons);

        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public static List<String> getListOfActions() {
        return List.of(PRODUCTS, EXIT);
    }

    private static List<KeyboardRow> getKeyboardRows(List<String> buttons) {
        List<KeyboardRow> keyboard = new ArrayList<>();

        int rowLength = 0;
        int i = 0;
        KeyboardRow row = new KeyboardRow();
        while (i < buttons.size()) {
            row.add(new KeyboardButton(buttons.get(i)));
            rowLength++;
            if (rowLength == MAX_ROW_SIZE_IN_KEYBOARD || i == buttons.size() - 1) {
                keyboard.add(row);
                row = new KeyboardRow();
                rowLength = 0;
            }
            i++;
        }
        return keyboard;
    }
}
