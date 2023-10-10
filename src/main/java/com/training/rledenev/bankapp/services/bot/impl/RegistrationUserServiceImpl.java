package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.services.UserService;
import com.training.rledenev.bankapp.services.bot.RegistrationUserService;
import com.training.rledenev.bankapp.services.bot.chatmaps.ChatIdInRegistrationMap;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
public class RegistrationUserServiceImpl implements RegistrationUserService {
    private static final String NAME_PATTERN = "[A-Za-z]+";
    private static final String PHONE_PATTERN = "^\\+\\d{1,3}-?\\d{3,14}$";
    private static final String ADDRESS_PATTERN = "[A-Za-z0-9\\s.,\\-'/\\\\]+";
    private static final String EMAIL_PATTERN =
            "(?!.*\\.{2})[A-Za-z0-9][A-Za-z0-9.]{4,28}[A-Za-z0-9]@[A-Za-z0-9.]+\\.[A-Za-z]{2,}";
    private static final String PASSWORD_PATTERN =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct})[a-zA-Z\\d\\p{Punct}]*$";
    private static final Map<Long, UserDto> CHAT_ID_USER_DTO_MAP = new ConcurrentHashMap<>();

    private final UserService userService;

    public RegistrationUserServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleRegistrationRequests(long chatId, String messageText, Update update) {
        if (!CHAT_ID_USER_DTO_MAP.containsKey(chatId)) {
            return fillInFirstName(chatId, messageText);
        }
        UserDto userDto = CHAT_ID_USER_DTO_MAP.get(chatId);
        if (userDto.getLastName() == null) {
            return fillInLastName(chatId, messageText, userDto);
        }
        if (userDto.getPhone() == null) {
            return fillInPhone(chatId, messageText, userDto);
        }
        if (userDto.getAddress() == null) {
            return fillInAddress(chatId, messageText, userDto);
        }
        if (userDto.getEmail() == null) {
            return fillInEmail(chatId, messageText, userDto);
        }
        return fillInPassword(chatId, messageText, userDto);
    }

    private SendMessage fillInFirstName(long chatId, String messageText) {
        UserDto userDto = new UserDto();
        if (isValidName(messageText)) {
            userDto.setFirstName(messageText);
            CHAT_ID_USER_DTO_MAP.put(chatId, userDto);
            return createSendMessage(chatId, ENTER_LAST_NAME);
        } else {
            return createSendMessage(chatId, INCORRECT_NAME);
        }
    }

    private SendMessage fillInLastName(long chatId, String messageText, UserDto userDto) {
        if (isValidName(messageText)) {
            userDto.setLastName(messageText);
            return createSendMessage(chatId, ENTER_PHONE);
        } else {
            return createSendMessage(chatId, INCORRECT_NAME);
        }
    }

    private SendMessage fillInPhone(long chatId, String messageText, UserDto userDto) {
        if (isValidPhone(messageText)) {
            userDto.setPhone(messageText);
            return createSendMessage(chatId, ENTER_ADDRESS);
        } else {
            return createSendMessage(chatId, INCORRECT_PHONE);
        }
    }

    private SendMessage fillInAddress(long chatId, String messageText, UserDto userDto) {
        if (isValidAddress(messageText)) {
            userDto.setAddress(messageText);
            return createSendMessage(chatId, ENTER_EMAIL);
        } else {
            return createSendMessage(chatId, INCORRECT_ADDRESS);
        }
    }

    private SendMessage fillInEmail(long chatId, String messageText, UserDto userDto) {
        if (isValidEmail(messageText)) {
            userDto.setEmail(messageText);
            return createSendMessage(chatId, ENTER_PASSWORD);
        } else {
            return createSendMessage(chatId, INCORRECT_EMAIL);
        }
    }

    private SendMessage fillInPassword(long chatId, String messageText, UserDto userDto) {
        if (isValidPassword(messageText)) {
            userDto.setPassword(messageText);
            userService.saveNewClient(userDto);
            CHAT_ID_USER_DTO_MAP.remove(chatId, userDto);
            ChatIdInRegistrationMap.put(chatId, false);
            return createSendMessageWithButtons(chatId, REGISTRATION_COMPLETED, List.of(REGISTER_USER, LOG_IN));
        } else {
            return createSendMessage(chatId, INCORRECT_PASSWORD);
        }
    }

    private boolean isValidName(String messageText) {
        return messageText.matches(NAME_PATTERN);
    }

    private boolean isValidPhone(String messageText) {
        return messageText.matches(PHONE_PATTERN);
    }

    private boolean isValidAddress(String messageText) {
        return messageText.matches(ADDRESS_PATTERN);
    }

    private boolean isValidEmail(String messageText) {
        return messageText.matches(EMAIL_PATTERN);
    }

    private boolean isValidPassword(String messageText) {
        return messageText.matches(PASSWORD_PATTERN);
    }
}
