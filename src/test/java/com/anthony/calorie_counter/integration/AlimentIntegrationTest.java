package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.dto.request.aliment.AlimentCreateDto;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.utils.factories.AlimentFactory;
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
@DisplayName("Integration test for Aliment API endpoints")
public class AlimentIntegrationTest extends TestBase {

    @Nested
    @DisplayName("Admin test cases")
    class AdminTestCases {

        @Test
        @DisplayName("Test if is possible create a new aliment and receive status code 201.")
        void canCreateANewMeal() throws Exception {
            AlimentCreateDto meal = AlimentFactory.alimentCreateDto();
            String valueAsString = objectMapper.writeValueAsString(meal);
            String path = ALIMENT_URL + "/create";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(meal.getName()))
                    .andExpect(jsonPath("$.portion").value(meal.getPortion()))
                    .andExpect(jsonPath("$.calories").value(meal.getCalories()))
                    .andExpect(jsonPath("$.totalFat").value(meal.getTotalFat()))
                    .andExpect(jsonPath("$.protein").value(meal.getProtein()))
                    .andExpect(jsonPath("$.carbohydrate").value(meal.getCarbohydrate()))
                    .andExpect(jsonPath("$.fiber").value(meal.getFiber()))
                    .andExpect(jsonPath("$.sugars").value(meal.getSugars()))
                    .andDo(print());
        }
    }
}
