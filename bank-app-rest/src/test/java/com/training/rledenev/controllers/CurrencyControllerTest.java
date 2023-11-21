package com.training.rledenev.controllers;

import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.services.CurrencyApiRequestService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyApiRequestService currencyApiRequestService;

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldGetCurrency() throws Exception {
        //given
        String mockEurRate = "4.5";
        JSONObject mockEurResponse = getMockJsonEurApiResponse(mockEurRate);

        //when
        when(currencyApiRequestService.getCurrencyJsonObject(CurrencyCode.EUR.toString())).thenReturn(mockEurResponse);
        String eurRateResult = mockMvc.perform(MockMvcRequestBuilders.get("/currency/" + CurrencyCode.EUR))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        Assertions.assertEquals(mockEurRate, eurRateResult);
        verify(currencyApiRequestService, times(1)).getCurrencyJsonObject(CurrencyCode.EUR.toString());
    }

    private JSONObject getMockJsonEurApiResponse(String mockEurRate) {
        return new JSONObject("{\n" +
                "    \"table\": \"A\",\n" +
                "    \"currency\": \"euro\",\n" +
                "    \"code\": \"EUR\",\n" +
                "    \"rates\": [\n" +
                "        {\n" +
                "            \"no\": \"209/A/NBP/2023\",\n" +
                "            \"effectiveDate\": \"2023-10-27\",\n" +
                "            \"mid\": " + mockEurRate + "\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }
}