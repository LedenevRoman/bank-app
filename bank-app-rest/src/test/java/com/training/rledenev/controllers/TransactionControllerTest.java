package com.training.rledenev.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.dto.TransactionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldGetAllTransactionsOfAccount() throws Exception {
        //given
        String accountNumber = "6123456789012345";
        List<TransactionDto> expected = getTransactionsOfThirdAccount();

        //when
        String allTransactionsOfAccountJson = mockMvc.perform(MockMvcRequestBuilders.get("/transaction/all")
                        .with(csrf())
                        .content(accountNumber))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<TransactionDto> allTransactionsDtoOfAccount = objectMapper.readValue(allTransactionsOfAccountJson,
                new TypeReference<>() {
                });

        Assertions.assertEquals(expected, allTransactionsDtoOfAccount);
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldCreateTransaction() throws Exception {
        //given
        TransactionDto transactionDto = getTransactionDto();
        String transactionDtoJson = objectMapper.writeValueAsString(transactionDto);

        String getAccountsOfUserBeforeTransactionResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/account/all/client"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<AccountDto> accountDtosOfUserBeforeTransaction = objectMapper
                .readValue(getAccountsOfUserBeforeTransactionResult, new TypeReference<>() {});

        AccountDto debitAccountDtoBeforeTransaction = accountDtosOfUserBeforeTransaction.get(0);
        AccountDto creditAccountDtoBeforeTransaction = accountDtosOfUserBeforeTransaction.get(1);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(transactionDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        String getAccountsOfUserAfterTransactionResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/account/all/client"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<AccountDto> accountDtosOfUserAfterTransaction = objectMapper.readValue(getAccountsOfUserAfterTransactionResult,
                new TypeReference<>() {});
        AccountDto debitAccountDtoAfterTransaction = accountDtosOfUserAfterTransaction.get(0);
        AccountDto creditAccountDtoAfterTransaction = accountDtosOfUserAfterTransaction.get(1);

        Assertions.assertNotEquals(debitAccountDtoBeforeTransaction.getBalance(),
                debitAccountDtoAfterTransaction.getBalance());
        Assertions.assertNotEquals(creditAccountDtoBeforeTransaction.getBalance(),
                creditAccountDtoAfterTransaction.getBalance());
        Assertions.assertEquals(debitAccountDtoBeforeTransaction.getBalance() - transactionDto.getAmount(),
                debitAccountDtoAfterTransaction.getBalance());
        Assertions.assertEquals(creditAccountDtoBeforeTransaction.getBalance() + transactionDto.getAmount(),
                creditAccountDtoAfterTransaction.getBalance());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldNotCreateTransactionNotEnoughMoney() throws Exception {
        //given
        TransactionDto transactionDto = getTransactionDto();
        transactionDto.setAmount(16000.0);
        String transactionDtoJson = objectMapper.writeValueAsString(transactionDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(transactionDtoJson))
                .andExpect(status().is(406));
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldNotCreateTransactionWrongType() throws Exception {
        //given
        TransactionDto transactionDto = getTransactionDto();
        transactionDto.setType("error");
        String transactionDtoJson = objectMapper.writeValueAsString(transactionDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(transactionDtoJson))
                .andExpect(status().is(500));
    }

    private TransactionDto getTransactionDto() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDebitAccountNumber("1234567890123456");
        transactionDto.setCreditAccountNumber("6123456789012345");
        transactionDto.setCurrencyCode("USD");
        transactionDto.setAmount(1000.0);
        transactionDto.setType("CASH");
        transactionDto.setDescription("test");
        return transactionDto;
    }

    private List<TransactionDto> getTransactionsOfThirdAccount() throws ParseException {
        TransactionDto transactionDto1 = new TransactionDto();
        transactionDto1.setDebitAccountNumber("1234567890123456");
        transactionDto1.setCreditAccountNumber("6123456789012345");
        transactionDto1.setAmount(1037.58);
        transactionDto1.setCurrencyCode("USD");
        transactionDto1.setDebitBalanceDifference(1037.0);
        transactionDto1.setCreditBalanceDifference(1037.0);
        transactionDto1.setType("CASH");
        transactionDto1.setDescription("for ice cream");
        transactionDto1.setCreatedAt(getDateFromString());

        TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setDebitAccountNumber("6123456789012345");
        transactionDto2.setCreditAccountNumber("4561234567890123");
        transactionDto2.setAmount(845.67);
        transactionDto2.setCurrencyCode("USD");
        transactionDto2.setDebitBalanceDifference(845.0);
        transactionDto2.setCreditBalanceDifference(845.0);
        transactionDto2.setType("CASH");
        transactionDto2.setDescription("for ice cream");
        transactionDto2.setCreatedAt(getDateFromString());

        return List.of(transactionDto1, transactionDto2);
    }

    private Date getDateFromString() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse("2023-09-03 12:00:00");
    }
}