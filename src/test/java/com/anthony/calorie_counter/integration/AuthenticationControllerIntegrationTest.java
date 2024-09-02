package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.integration.config.TestBase;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@DisplayName("Integration test for Authentication API endpoint")
public class AuthenticationControllerIntegrationTest  extends TestBase {

    @Test
    @DisplayName("Test if is possible authenticate a user and receive status code 200.")
    void canAuthenticateAUser() throws Exception {
        MvcResult response = mockMvc.perform(post(AUTH_LOGIN_URL)
                        .with(httpBasic(savedUser().getEmail(), savedUser().getPassword()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();
        String contentAsString = response.getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);
        String token = "Bearer " + json.getString("token");
        String path = USER_URL + "/" + savedUser().getId();
        mockMvc.perform(get(path).header("Authorization", token))
                .andExpect(status().isOk());
    }
}
