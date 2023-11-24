package com.training.rledenev.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.dto.ErrorData;
import com.training.rledenev.dto.ProductDto;
import com.training.rledenev.entity.enums.ProductType;
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
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
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

        Assertions.assertEquals("Auto Loan", receivedSuitableProduct.getName());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void findSuitableProductGetOutOfLimitMessageNegativeCase() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setProductType("LOAN");
        agreementDto.setCurrencyCode("EUR");
        agreementDto.setSum(00000.0);

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
    void findSuitableCardTypePositiveCase() throws Exception {
        // given
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setProductType("DEBIT_CARD");
        agreementDto.setCurrencyCode("EUR");
        agreementDto.setSum(00000.0);

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
        Assertions.assertEquals(ProductType.DEBIT_CARD.getName(), receivedSuitableProduct.getType());
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldFindAllActiveProduct() throws Exception {
        //given
        List<ProductDto> expected = getAllActiveProductDtos();

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

        Assertions.assertEquals(expected, receivedProductsAllActive);

    }

    private static List<ProductDto> getAllActiveProductDtos() {
        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Auto Loan");
        productDto1.setType("Loan");
        productDto1.setMinLimit(60000);
        productDto1.setInterestRate(4.5);
        productDto1.setPeriodMonths(60);

        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Mortgage Loan");
        productDto2.setType("Loan");
        productDto2.setMinLimit(250000);
        productDto2.setInterestRate(3.2);
        productDto2.setPeriodMonths(240);

        ProductDto productDto3 = new ProductDto();
        productDto3.setName("Travel Loan");
        productDto3.setType("Loan");
        productDto3.setMinLimit(8000);
        productDto3.setInterestRate(8.2);
        productDto3.setPeriodMonths(12);

        ProductDto productDto4 = new ProductDto();
        productDto4.setName("Pension Savings Deposit");
        productDto4.setType("Deposit");
        productDto4.setMinLimit(30000);
        productDto4.setInterestRate(3.8);
        productDto4.setPeriodMonths(120);

        ProductDto productDto5 = new ProductDto();
        productDto5.setName("Children's Savings Deposit");
        productDto5.setType("Deposit");
        productDto5.setMinLimit(5000);
        productDto5.setInterestRate(4.5);
        productDto5.setPeriodMonths(60);

        ProductDto productDto6 = new ProductDto();
        productDto6.setName("VIP Deposit");
        productDto6.setType("Deposit");
        productDto6.setMinLimit(100000);
        productDto6.setInterestRate(4.8);
        productDto6.setPeriodMonths(24);

        ProductDto productDto7 = new ProductDto();
        productDto7.setName("Credit card");
        productDto7.setType("Credit card");
        productDto7.setMinLimit(10000);
        productDto7.setInterestRate(18.0);
        productDto7.setPeriodMonths(60);

        ProductDto productDto8 = new ProductDto();
        productDto8.setName("Debit card");
        productDto8.setType("Debit card");
        productDto8.setMinLimit(0);
        productDto8.setInterestRate(0.0);
        productDto8.setPeriodMonths(60);

        return List.of(productDto1, productDto2, productDto3, productDto4,
                productDto5, productDto6, productDto7, productDto8);
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldFindAllActiveByTypeProduct() throws Exception {
        // given
        String type = ProductType.LOAN.toString();
        List<ProductDto> expected = getProductDtos();

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

        Assertions.assertEquals(expected, receivedProductsAllActiveByType);
    }

    private static List<ProductDto> getProductDtos() {
        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Auto Loan");
        productDto1.setType(ProductType.LOAN.getName());
        productDto1.setMinLimit(60000);
        productDto1.setInterestRate(4.5);
        productDto1.setPeriodMonths(60);

        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Mortgage Loan");
        productDto2.setType(ProductType.LOAN.getName());
        productDto2.setMinLimit(250000);
        productDto2.setInterestRate(3.2);
        productDto2.setPeriodMonths(240);

        ProductDto productDto3 = new ProductDto();
        productDto3.setName("Travel Loan");
        productDto3.setType(ProductType.LOAN.getName());
        productDto3.setMinLimit(8000);
        productDto3.setInterestRate(8.2);
        productDto3.setPeriodMonths(12);

        return List.of(productDto1, productDto2, productDto3);
    }
}