package com.training.rledenev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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

        String agreementStr = objectMapper.writeValueAsString(agreementDto);

        // when
        MvcResult productGetSuitableResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/suitable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(agreementStr))
                .andReturn();

        String productGetSuitableResultJson = productGetSuitableResult.getResponse().getContentAsString();
        ProductDto receivedSuitableProduct = objectMapper.readValue(productGetSuitableResultJson, ProductDto.class);
        agreementDto.setProductName(receivedSuitableProduct.getName());
        agreementStr = objectMapper.writeValueAsString(agreementDto);

        MvcResult agreementCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/agreement/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(agreementStr))
                .andReturn();

        long id = Long.parseLong(agreementCreationResult.getResponse().getContentAsString());

        MvcResult agreementGetResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/agreement/" + id))
                .andReturn();

        // then
        Assertions.assertEquals(201, agreementCreationResult.getResponse().getStatus());
        Assertions.assertEquals(200, agreementGetResult.getResponse().getStatus());

        String agreementGetStringJson = agreementGetResult.getResponse().getContentAsString();
        AgreementDto receivedAgreementJson = objectMapper.readValue(agreementGetStringJson, AgreementDto.class);

        Assertions.assertEquals(agreementDto.getProductType(), receivedAgreementJson.getProductType());
        Assertions.assertEquals(agreementDto.getCurrencyCode(), receivedAgreementJson.getCurrencyCode());
        Assertions.assertEquals(agreementDto.getSum(), receivedAgreementJson.getSum());
    }
}