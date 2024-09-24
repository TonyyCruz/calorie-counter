package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.dto.request.aliment.AlimentDto;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.utils.factories.AlimentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        void canCreateANewAliment() throws Exception {
            AlimentDto aliment = AlimentFactory.alimentDto();
            String valueAsString = objectMapper.writeValueAsString(aliment);
            String path = ALIMENT_URL + "/create";
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(aliment.getName()))
                    .andExpect(jsonPath("$.portion").value(aliment.getPortion()))
                    .andExpect(jsonPath("$.calories").value(aliment.getCalories()))
                    .andExpect(jsonPath("$.totalFat").value(aliment.getTotalFat()))
                    .andExpect(jsonPath("$.protein").value(aliment.getProtein()))
                    .andExpect(jsonPath("$.carbohydrate").value(aliment.getCarbohydrate()))
                    .andExpect(jsonPath("$.fiber").value(aliment.getFiber()))
                    .andExpect(jsonPath("$.sugars").value(aliment.getSugars()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if is possible update an aliment by id and receive status code 200.")
        void canUpdateAnAlimentById() throws Exception {
            AlimentDto update = AlimentFactory.alimentDto();
            String valueAsString = objectMapper.writeValueAsString(update);
            String path = ALIMENT_URL + "/" + savedAliment().getId();
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(update.getName()))
                    .andExpect(jsonPath("$.portion").value(update.getPortion()))
                    .andExpect(jsonPath("$.calories").value(update.getCalories()))
                    .andExpect(jsonPath("$.totalFat").value(update.getTotalFat()))
                    .andExpect(jsonPath("$.protein").value(update.getProtein()))
                    .andExpect(jsonPath("$.carbohydrate").value(update.getCarbohydrate()))
                    .andExpect(jsonPath("$.fiber").value(update.getFiber()))
                    .andExpect(jsonPath("$.sugars").value(update.getSugars()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if is possible delete an aliment by id and receive status code 204.")
        void canDeleteAnAlimentById() throws Exception {
            String path = ALIMENT_URL + "/" + savedAliment().getId();
            mockMvc.perform(delete(path).header("Authorization", adminToken))
                    .andExpect(status().isNoContent())
                    .andDo(print());
            mockMvc.perform(get(path).header("Authorization", adminToken))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("User test cases")
    class UserTestCases {

        @Test
        @DisplayName("Test if is possible find an aliment by id and receive status code 200.")
        void canFindAnAlimentById() throws Exception {
            String path = ALIMENT_URL + "/" + savedAliment().getId();
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(savedAliment().getId()))
                    .andExpect(jsonPath("$.name").value(savedAliment().getName()))
                    .andExpect(jsonPath("$.portion").value(savedAliment().getPortion()))
                    .andExpect(jsonPath("$.calories").value(savedAliment().getCalories()))
                    .andExpect(jsonPath("$.totalFat").value(savedAliment().getTotalFat()))
                    .andExpect(jsonPath("$.protein").value(savedAliment().getProtein()))
                    .andExpect(jsonPath("$.carbohydrate").value(savedAliment().getCarbohydrate()))
                    .andExpect(jsonPath("$.fiber").value(savedAliment().getFiber()))
                    .andExpect(jsonPath("$.sugars").value(savedAliment().getSugars()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if is possible find an aliment by name and receive status code 200.")
        void canFindAnAlimentByName() throws Exception {
            String[] slicedName = savedAliment().getName().split(" ");
            String path = ALIMENT_URL + "?name=" + slicedName[0];
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]").value(savedAliment()))
                    .andDo(print());
            path = ALIMENT_URL + "?name=" + slicedName[1].toUpperCase();
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]").value(savedAliment()))
                    .andDo(print());
            path = ALIMENT_URL + "?name=" + slicedName[2].toLowerCase();
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]").value(savedAliment()))
                    .andDo(print());
            path = ALIMENT_URL + "?name=" + savedAliment().getName();
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]").value(savedAliment()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if trying get an aliment with invalid name returns an empty list and receive status code 200.")
        void receiveAnEmptyListIfAlimentNameSearchedWasNotFound() throws Exception {
            String invalidName = "some invalid";
            String path = ALIMENT_URL + "?name=" + invalidName;
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*]").isEmpty())
                    .andDo(print());
        }
    }
}
