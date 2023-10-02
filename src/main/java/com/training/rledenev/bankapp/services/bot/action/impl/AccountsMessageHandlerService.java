package com.training.rledenev.bankapp.services.bot.action.impl;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.dto.TransactionDto;
import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.services.AccountService;
import com.training.rledenev.bankapp.services.TransactionService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.transaction.TransactionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.transaction.impl.TransactionMessageHandlerServiceImpl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
public class AccountsMessageHandlerService implements ActionMessageHandlerService {
    public static final Map<Long, AccountDto> CHAT_ID_ACCOUNT_DTO_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, Boolean> CHAT_ID_IS_MAKING_TRANSACTION = new ConcurrentHashMap<>();
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransactionMessageHandlerService transactionMessageHandlerService;

    public AccountsMessageHandlerService(AccountService accountService, TransactionService transactionService,
                                         TransactionMessageHandlerService transactionMessageHandlerService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.transactionMessageHandlerService = transactionMessageHandlerService;
    }

    @Override
    public SendMessage handleMessage(long chatId, String message, Role userRole) {
        List<AccountDto> accountDtos = accountService.getAccountsForClient();
        if (CHAT_ID_ACCOUNT_DTO_MAP.get(chatId) == null) {
            if (message.equals(MY_ACCOUNTS)) {
                return createSendMessageWithButtons(chatId, getMyAccountsMessage(accountDtos),
                        getMyAccountsButtons(accountDtos));
            }
            int accountUserIndex;
            try {
                accountUserIndex = Integer.parseInt(message);
            } catch (NumberFormatException exception) {
                return createSendMessageWithButtons(chatId, INCORRECT_NUMBER_INT,
                        getMyAccountsButtons(accountDtos));
            }
            if (accountUserIndex > 0 && accountUserIndex <= accountDtos.size()) {
                AccountDto accountDto = accountDtos.get(accountUserIndex - 1);
                CHAT_ID_ACCOUNT_DTO_MAP.put(chatId, accountDto);
                return createSendMessageWithButtons(chatId, getCustomAccountInfo(accountDto),
                        getListOfActionsForClientAccount());
            } else {
                return createSendMessageWithButtons(chatId, WRONG_ACCOUNT_INDEX,
                        getMyAccountsButtons(accountDtos));
            }
        } else {
            if (message.equals(BACK_TO_LIST_ACCOUNTS)) {
                CHAT_ID_ACCOUNT_DTO_MAP.remove(chatId);
                CHAT_ID_IS_MAKING_TRANSACTION.remove(chatId);
                TransactionMessageHandlerServiceImpl.CHAT_ID_TRANSACTION_DTO_MAP.remove(chatId);
                return createSendMessageWithButtons(chatId, getMyAccountsMessage(accountDtos),
                        getMyAccountsButtons(accountDtos));
            }
            AccountDto accountDto = CHAT_ID_ACCOUNT_DTO_MAP.get(chatId);
            if (Boolean.TRUE.equals(CHAT_ID_IS_MAKING_TRANSACTION.get(chatId))) {
                return transactionMessageHandlerService.handleMessage(chatId, message, accountDto);
            } else if (message.equals(MAKE_TRANSACTION)) {
                CHAT_ID_IS_MAKING_TRANSACTION.put(chatId, true);
                return transactionMessageHandlerService.handleMessage(chatId, message, accountDto);
            }
            if (message.equals(VIEW_ALL_TRANSACTIONS)) {
                List<TransactionDto> allTransactionsDto = transactionService
                        .getAllTransactionsOfAccount(accountDto.getNumber());
                return createSendMessageWithButtons(chatId, getAllTransactionsMessage(accountDto, allTransactionsDto),
                        List.of(BACK_TO_LIST_ACCOUNTS));
            }
        }

        return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
    }

    private String getAllTransactionsMessage(AccountDto accountDto, List<TransactionDto> allTransactionsDto) {
        StringBuilder stringBuilder = new StringBuilder(LIST_TRANSACTIONS);
        allTransactionsDto.forEach(transactionDto -> {
            if (transactionDto.getDebitAccountNumber().equals(accountDto.getNumber())) {
                getDebitAccountMessage(stringBuilder, transactionDto, accountDto.getCurrencyCode());
            } else {
                getCreditAccountMessage(stringBuilder, transactionDto, accountDto.getCurrencyCode());
            }
        });

        return stringBuilder.toString();
    }

    private void getDebitAccountMessage(StringBuilder stringBuilder, TransactionDto transactionDto,
                                        String accountCurrency) {
        if (transactionDto.getCurrencyCode().equals(accountCurrency)) {
            stringBuilder.append(String.format(AMOUNT_IN_SAME_CURRENCY_DEBIT_TRANSACTION_INFO,
                    transactionDto.getAmount(), transactionDto.getCurrencyCode(),
                    transactionDto.getCreditAccountNumber()));
        } else {
            stringBuilder.append(String.format(AMOUNT_DEBIT_TRANSACTION_INFO, transactionDto.getAmount(),
                    transactionDto.getCurrencyCode(), transactionDto.getDebitBalanceDifference(), accountCurrency,
                    transactionDto.getCreditAccountNumber()));
        }
        appendAnotherInfo(stringBuilder, transactionDto);
    }

    private void getCreditAccountMessage(StringBuilder stringBuilder, TransactionDto transactionDto,
                                         String accountCurrency) {
        if (transactionDto.getCurrencyCode().equals(accountCurrency)) {
            stringBuilder.append(String.format(AMOUNT_IN_SAME_CURRENCY_CREDIT_TRANSACTION_INFO,
                    transactionDto.getAmount(), transactionDto.getCurrencyCode(),
                    transactionDto.getDebitAccountNumber()));
        } else {
            stringBuilder.append(String.format(AMOUNT_CREDIT_TRANSACTION_INFO, transactionDto.getAmount(),
                    transactionDto.getCurrencyCode(), transactionDto.getCreditBalanceDifference(), accountCurrency,
                    transactionDto.getDebitAccountNumber()));
        }
        appendAnotherInfo(stringBuilder, transactionDto);
    }

    private static void appendAnotherInfo(StringBuilder stringBuilder, TransactionDto transactionDto) {
        stringBuilder.append(String.format(Locale.ENGLISH, ANOTHER_TRANSACTION_INFO, transactionDto.getCreatedAt(),
                transactionDto.getType(), transactionDto.getDescription()));
    }

    private String getCustomAccountInfo(AccountDto accountDto) {
        LocalDate startDate = accountDto.getStartDate();
        LocalDate paymentTerm = accountDto.getPaymentTerm();
        return String.format(FULL_ACCOUNT_INFO, accountDto.getNumber(), accountDto.getOwner(),
                accountDto.getProductName(), accountDto.getInterestRate(), startDate, startDate, startDate, paymentTerm,
                paymentTerm, paymentTerm, accountDto.getBalance(), accountDto.getCurrencyName());
    }

    private List<String> getMyAccountsButtons(List<AccountDto> accountDtos) {
        List<String> accountsIndex = new ArrayList<>();
        for (int i = 0; i < accountDtos.size(); i++) {
            accountsIndex.add(String.valueOf(i + 1));
        }
        accountsIndex.add(BACK);
        return accountsIndex;
    }

    private String getMyAccountsMessage(List<AccountDto> accountDtos) {
        StringBuilder stringBuilder = new StringBuilder(MY_ACCOUNTS_LIST);
        for (int i = 0; i < accountDtos.size(); i++) {
            AccountDto accountDto = accountDtos.get(i);
            stringBuilder.append(String.format(SHORT_ACCOUNT_INFO, i + 1, accountDto.getNumber(),
                            accountDto.getProductName(), accountDto.getBalance(), accountDto.getCurrencyCode()))
                    .append("\n")
                    .append("\n");
        }
        stringBuilder.append("\n")
                .append(SELECT_ACCOUNT);
        return stringBuilder.toString();
    }
}
