package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.utils.factories.RoleFactory;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@DisplayName("Integration test for Meal API endpoints")
public class MealIntegrationTest extends TestBase {

    @Nested
    @DisplayName("Admin test cases")
    class AdminTestCases {

        @Test
        @DisplayName("Test if is possible create a new user and receive status code 201.")
        void canCreateANewMeal() throws Exception {
            UserCreateDto newUser = UserFactory.userCreateDto();
            String valueAsString = objectMapper.writeValueAsString(newUser);
            String path = USER_URL + "/register";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(newUser.getName()))
                    .andExpect(jsonPath("$.email").value(newUser.getEmail()))
                    .andExpect(jsonPath("$.phoneNumber").value(newUser.getPhoneNumber()))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.roles", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$.roles[0]").value(RoleFactory.createUserRole()))
                    .andDo(print());
        }
    }
}
