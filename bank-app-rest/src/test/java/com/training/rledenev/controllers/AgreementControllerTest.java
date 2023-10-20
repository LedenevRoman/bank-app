package com.training.rledenev.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.entity.security.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class AgreementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldCreateAgreement() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setProductType("LOAN");
        agreementDto.setCurrencyCode("EUR");
        agreementDto.setSum(50000.0);
        agreementDto.setProductName("Auto Loan");

        String agreementStr = objectMapper.writeValueAsString(agreementDto);

        // when
        MvcResult agreementCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/agreement/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(agreementStr))
                .andExpect(status().isCreated())
                .andReturn();

        long id = Long.parseLong(agreementCreationResult.getResponse().getContentAsString());

        MvcResult agreementGetResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/agreement/" + id))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String agreementGetStringJson = agreementGetResult.getResponse().getContentAsString();
        AgreementDto receivedAgreementJson = objectMapper.readValue(agreementGetStringJson, AgreementDto.class);

        Assertions.assertEquals(agreementDto.getProductType(), receivedAgreementJson.getProductType());
        Assertions.assertEquals(agreementDto.getCurrencyCode(), receivedAgreementJson.getCurrencyCode());
        Assertions.assertEquals(agreementDto.getSum(), receivedAgreementJson.getSum());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldGetAllNewAgreements() throws Exception {
        // given
        List<AgreementDto> expected = getNewAgreements();

        // when
        MvcResult newAgreementsResult = mockMvc.perform(MockMvcRequestBuilders.get("/agreement/new"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String newAgreementDtosJson = newAgreementsResult.getResponse().getContentAsString();
        List<AgreementDto> newAgreementDtos = objectMapper.readValue(newAgreementDtosJson, new TypeReference<>() {
        });

        Assertions.assertEquals(expected, newAgreementDtos);
    }

    @Test
    void shouldConfirmAgreement() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setId(3L);
        agreementDto.setSum(11000.0);
        agreementDto.setInterestRate(3.8);

        UsernamePasswordAuthenticationToken authClient = getAuthenticationToken(getClient());
        UsernamePasswordAuthenticationToken authManager = getAuthenticationToken(getManager());

        // when
        SecurityContextHolder.getContext().setAuthentication(authClient);
        MvcResult getAccountNotConfirmedResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/account/all/client"))
                .andExpect(status().isOk())
                .andReturn();

        SecurityContextHolder.getContext().setAuthentication(authManager);
        mockMvc.perform(MockMvcRequestBuilders.put("/agreement/confirm/" + agreementDto.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        SecurityContextHolder.getContext().setAuthentication(authClient);
        MvcResult getAccountConfirmedResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/account/all/client"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String accountNotConfirmedDtosJson = getAccountNotConfirmedResult.getResponse().getContentAsString();
        List<AccountDto> allAccountsNotConfirmedDtoOfUser = objectMapper.readValue(accountNotConfirmedDtosJson,
                new TypeReference<>() {});
        AccountDto notConfirmedAccount = allAccountsNotConfirmedDtoOfUser.get(0);

        String confirmedAccountDtosJson = getAccountConfirmedResult.getResponse().getContentAsString();
        List<AccountDto> allAccountsConfirmedDtoOfUser = objectMapper.readValue(confirmedAccountDtosJson,
                new TypeReference<>() {});
        AccountDto confirmedAccount = allAccountsConfirmedDtoOfUser.get(0);

        Assertions.assertEquals(0.0, notConfirmedAccount.getBalance());
        Assertions.assertNull(notConfirmedAccount.getStartDate());
        Assertions.assertNull(notConfirmedAccount.getPaymentTerm());
        Assertions.assertEquals(agreementDto.getSum(), confirmedAccount.getBalance());
        Assertions.assertNotNull(confirmedAccount.getStartDate());
        Assertions.assertNotNull(confirmedAccount.getPaymentTerm());

    }

    @Test
    @WithUserDetails(value = "james.harris@yopmail.com")
    void shouldBlockAgreement() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setId(3L);

        String blockedAccountNumber = "4561234567890123";

        UsernamePasswordAuthenticationToken authClient = getAuthenticationToken(getClient());
        UsernamePasswordAuthenticationToken authManager = getAuthenticationToken(getManager());

        // when
        SecurityContextHolder.getContext().setAuthentication(authClient);
        MvcResult getAccountNotConfirmedResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/account/all/client"))
                .andExpect(status().isOk())
                .andReturn();

        SecurityContextHolder.getContext().setAuthentication(authManager);
        mockMvc.perform(MockMvcRequestBuilders.put("/agreement/block/" + agreementDto.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        SecurityContextHolder.getContext().setAuthentication(authClient);
        MvcResult getAccountConfirmedResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/account/all/client"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String accountDtosBeforeBlockedJson = getAccountNotConfirmedResult.getResponse().getContentAsString();
        List<AccountDto> allAccountDtosOfUserBeforeBlocked = objectMapper.readValue(accountDtosBeforeBlockedJson,
                new TypeReference<>() {});
        AccountDto blockedAccountDto = allAccountDtosOfUserBeforeBlocked.get(0);

        String accountDtosAfterBlockedJson = getAccountConfirmedResult.getResponse().getContentAsString();
        List<AccountDto> allAccountsDtoOfUserAfterBlocking = objectMapper.readValue(accountDtosAfterBlockedJson,
                new TypeReference<>() {});
        AccountDto notBlockedAccountDto = allAccountsDtoOfUserAfterBlocking.get(0);

        Assertions.assertEquals(blockedAccountNumber, blockedAccountDto.getNumber());
        Assertions.assertEquals(2, allAccountDtosOfUserBeforeBlocked.size());
        Assertions.assertNotEquals(blockedAccountNumber, notBlockedAccountDto.getNumber());
        Assertions.assertEquals(1, allAccountsDtoOfUserAfterBlocking.size());
    }

    private static UsernamePasswordAuthenticationToken getAuthenticationToken(User user) {
        UserDetails userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
    }

    private static User getManager() {
        User manager = new User();
        manager.setId(3L);
        manager.setRole(Role.MANAGER);
        manager.setFirstName("Mia");
        manager.setLastName("Clark");
        manager.setEmail("mia.clark@yopmail.com");
        return manager;
    }

    private static User getClient() {
        User client = new User();
        client.setId(2L);
        client.setRole(Role.CLIENT);
        client.setFirstName("James");
        client.setLastName("Harris");
        client.setEmail("james.harris@yopmail.com");
        return client;
    }

    private static List<AgreementDto> getNewAgreements() {
        AgreementDto agreementDto1 = new AgreementDto();
        agreementDto1.setId(3L);
        agreementDto1.setProductType("LOAN");
        agreementDto1.setProductName("Auto Loan");
        agreementDto1.setCurrencyCode("EUR");
        agreementDto1.setSum(11000.0);
        agreementDto1.setPeriodMonths(60);
        agreementDto1.setInterestRate(4.5);

        AgreementDto agreementDto2 = new AgreementDto();
        agreementDto2.setId(4L);
        agreementDto2.setProductType("DEBIT_CARD");
        agreementDto2.setProductName("Debit card");
        agreementDto2.setCurrencyCode("EUR");
        agreementDto2.setSum(11000.0);
        agreementDto2.setPeriodMonths(60);
        agreementDto2.setInterestRate(0.0);

        return List.of(agreementDto1, agreementDto2);
    }
}