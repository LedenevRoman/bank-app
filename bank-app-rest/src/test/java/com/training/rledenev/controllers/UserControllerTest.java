package com.training.rledenev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.rledenev.dto.UserDto;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "isabella.white@yopmail.com")
    void shouldCreateUser() throws Exception {
        //given
        UserDto userDto = getUserDto();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        //when
        String createdUserId = mockMvc.perform(MockMvcRequestBuilders.post("/user/new-client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(userDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        String createdUserJson = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + createdUserId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto createdUser = objectMapper.readValue(createdUserJson, UserDto.class);

        userDto.setPassword(null);
        Assertions.assertEquals(userDto, createdUser);
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@gmail.com");
        userDto.setAddress("test, address");
        userDto.setPhone("+123456789");
        userDto.setPassword("P@ssword1");
        return userDto;
    }
}