package com.training.rledenev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.security.jwt.JwtProvider;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldGetAuthRole() throws Exception {
        // when
        MvcResult getAuthRoleResult = mockMvc.perform(MockMvcRequestBuilders.get("/auth"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String actualRoleJson = getAuthRoleResult.getResponse().getContentAsString();
        Role actualRole = objectMapper.readValue(actualRoleJson, Role.class);

        Assertions.assertEquals(Role.CLIENT, actualRole);
    }

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldAuthenticateUser() throws Exception {
        // given
        UserDto userDto = new UserDto();
        userDto.setEmail("mia.clark@yopmail.com");
        userDto.setPassword("P@ssword1");

        String userDtoStr = objectMapper.writeValueAsString(userDto);

        // when
        MvcResult postAuthTokenResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(userDtoStr))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String securityToken = postAuthTokenResult.getResponse().getContentAsString();
        String actualEmail = jwtProvider.getEmailFromToken(securityToken);

        Assertions.assertEquals(userDto.getEmail(), actualEmail);
    }
}