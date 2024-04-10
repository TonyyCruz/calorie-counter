package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTest extends TestBase {
    @Autowired
    private UserRepository userRepository;

    @Test @DisplayName("Test if is possible create a new user.")
    void canCreateAnNewUser() throws Exception {
        UserDto userDto = UserFactory.createUserDto();
        String valueAsString = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.fullName").value(userDto.fullName()))
            .andExpect(jsonPath("$.email").value(userDto.email()))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test @DisplayName("Test if is possible find a user by received id.")
    void canFindAnUserById() throws Exception {
        User user = UserFactory.createUser();
        userRepository.save(user);
        mockMvc.perform(get(USER_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}
