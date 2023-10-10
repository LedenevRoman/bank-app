package com.training.rledenev.bankapp.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.dto.ErrorData;
import com.training.rledenev.bankapp.dto.ProductDto;
import com.training.rledenev.bankapp.entity.enums.ProductType;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schema-cleanup.sql")
@Sql("/db/migration/V1.0.0__create_tables.sql")
@Sql("/db/migration/V1.0.1__add_test_data.sql")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void findSuitableProductPositiveCase() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setProductType("LOAN");
        agreementDto.setCurrencyCode("EUR");
        agreementDto.setSum(50000.0);
        agreementDto.setPeriodMonths(40);

        String agreementStr = objectMapper.writeValueAsString(agreementDto);

        // when
        MvcResult productGetSuitableResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/suitable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(agreementStr))
                .andReturn();

        // then
        Assertions.assertEquals(200, productGetSuitableResult.getResponse().getStatus());

        String productGetSuitableResultJson = productGetSuitableResult.getResponse().getContentAsString();
        ProductDto receivedSuitableProduct = objectMapper.readValue(productGetSuitableResultJson, ProductDto.class);

        Assertions.assertEquals("Small Business Loan", receivedSuitableProduct.getName());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void findSuitableProductGetOutOfLimitMessageNegativeCase() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setProductType("LOAN");
        agreementDto.setCurrencyCode("EUR");
        agreementDto.setSum(00000.0);
        agreementDto.setPeriodMonths(40);

        String agreementStr = objectMapper.writeValueAsString(agreementDto);

        // when
        MvcResult productGetSuitableResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/suitable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(agreementStr))
                .andReturn();

        // then
        Assertions.assertEquals(204, productGetSuitableResult.getResponse().getStatus());


        String productGetSuitableResultJson = productGetSuitableResult.getResponse().getContentAsString();
        ErrorData errorData = objectMapper.readValue(productGetSuitableResultJson, ErrorData.class);

        Assertions.assertEquals("Amount or period is out of limit", errorData.getMessage());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void findSuitableProductNoTypeMessageNegativeCase() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setProductType("DEBIT_CARD");
        agreementDto.setCurrencyCode("EUR");
        agreementDto.setSum(00000.0);
        agreementDto.setPeriodMonths(40);

        String agreementStr = objectMapper.writeValueAsString(agreementDto);

        // when
        MvcResult productGetSuitableResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/suitable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(agreementStr))
                .andReturn();

        // then
        Assertions.assertEquals(200, productGetSuitableResult.getResponse().getStatus());

        String productGetSuitableResultJson = productGetSuitableResult.getResponse().getContentAsString();
        ProductDto receivedSuitableProduct = objectMapper.readValue(productGetSuitableResultJson, ProductDto.class);

        Assertions.assertEquals("Debit card", receivedSuitableProduct.getName());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldFindAllActiveProduct() throws Exception {
        // when
        MvcResult productsGetAllActiveResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/all-active")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andReturn();

        // then
        Assertions.assertEquals(200, productsGetAllActiveResult.getResponse().getStatus());

        String productsGetAllActiveJson = productsGetAllActiveResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllActive = objectMapper.readValue(productsGetAllActiveJson,
                new TypeReference<>() {});

        Assertions.assertEquals(17, receivedProductsAllActive.size());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldFindAllActiveByTypeProduct() throws Exception {
        // given
        String type = ProductType.LOAN.toString();

        // when
        MvcResult productsGetAllActiveByTypeResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/product/all-active/" + type)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andReturn();

        // then
        Assertions.assertEquals(200, productsGetAllActiveByTypeResult.getResponse().getStatus());

        String productGetAllActiveByTypeResultJson = productsGetAllActiveByTypeResult.getResponse().getContentAsString();
        List<ProductDto> receivedProductsAllActiveByType = objectMapper.readValue(productGetAllActiveByTypeResultJson,
                new TypeReference<>() {});

        Assertions.assertEquals(9, receivedProductsAllActiveByType.size());
    }
}