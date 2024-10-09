package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.entity.dto.request.aliment.AlimentDto;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.utils.factories.AlimentFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
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
            mockMvc.perform(post(ALIMENT_URL)
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

        @Test
        @DisplayName("Test if throws an exception when try to create an aliment by invalid name and receive status code 400.")
        void cannotCreateAnAlimentByInvalidName() throws Exception {
            AlimentDto aliment = AlimentFactory.alimentDto();
            String[] invalidNames = {"a", "a".repeat(51)};
            for(String name : invalidNames) {
                aliment.setName(name);
                String valueAsString = objectMapper.writeValueAsString(aliment);
                mockMvc.perform(post(ALIMENT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                                .header("Authorization", adminToken)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.path").value(ALIMENT_URL))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath("$.fieldError[0].fieldName").value("name"))
                        .andExpect(jsonPath("$.fieldError[0].errorMessage").value(
                                "The name length must be between 2 and 50 characters"
                        ))
                        .andDo(print());
            }
        }

        @Test
        @DisplayName("Test if throws an exception when try to create an aliment by empty values and receive status code 400.")
        void cannotCreateAnAlimentByEmptyValues() throws Exception {
            AlimentDto alimentDto = new AlimentDto("", "", null, "", "", "", "", "");
            String valueAsString = objectMapper.writeValueAsString(alimentDto);
            mockMvc.perform(post(ALIMENT_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.path").value(ALIMENT_URL))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath(
                            "$.fieldError[*].fieldName",
                            Matchers.containsInAnyOrder(
                                    "name", "name",
                                    "portion", "portion",
                                    "calories",
                                    "totalFat", "totalFat",
                                    "protein", "protein",
                                    "carbohydrate", "carbohydrate",
                                    "fiber", "fiber",
                                    "sugars", "sugars"
                            ))
                    )
                    .andExpect(jsonPath(
                            "$.fieldError[*].errorMessage",
                            Matchers.containsInAnyOrder(
                                    "The name must not be empty.",
                                    "The name length must be between 2 and 50 characters",
                                    "The portion must not be empty.",
                                    "The calories must not be null.",
                                    "The totalFat must not be empty.",
                                    "The protein must not be empty.",
                                    "The carbohydrate must not be empty.",
                                    "The fiber must not be empty.",
                                    "The sugars must not be empty.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'."
                            ))
                    )
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when try to create an aliment by invalid values format and receive status code 400.")
        void cannotCreateAnAlimentByInvalidValuesFormat() throws Exception {
            AlimentDto[] invalidAlimentsDto = {
                    new AlimentDto("Test", "0", 1, "0", "0", "0", "0", "0"),
                    new AlimentDto("Test", "0 g", 10, "5 g", "0 g", "0 g", "3 g", "2 g"),
                    new AlimentDto("Test", "0.1", 8, "3.5", "8.6", "0.0", "9.7", "4.9"),
                    new AlimentDto("Test", "0 .1", 21, "3. 5", "8 . 6", "0  .0", "9.  7", "4  .  9"),
                    new AlimentDto("Test", "0.1 g", 14, "3.5 g", "8.6 g", "0.0 g", "9.7 g", "4.9 g"),
                    new AlimentDto("Test", "0 .1g", 23, "3. 5g", "8 . 6g", "0  .0g", "9.  7g", "4  .  9g"),
                    new AlimentDto("Test", "0.15", 22, "3.57", "8.68", "0.08", "9.72", "4.99"),
                    new AlimentDto("Test", "0.1 5", 34, "3. 57", "8 .68", "0 . 08", "9. 7 2", "4 .9 9"),
                    new AlimentDto("Test", "0.1 5g", 7, "3. 57g", "8 .68g", "0 . 08g", "9. 7 2g", "4 .9 9g"),
                    new AlimentDto("Test", "0.151g", 9, "3.572g", "8.6844g", "0.08578g", "9.7201g", "4.993g")
            };
            for (AlimentDto alimentDto : invalidAlimentsDto) {
                String valueAsString = objectMapper.writeValueAsString(alimentDto);
                mockMvc.perform(post(ALIMENT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                                .header("Authorization", adminToken)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.path").value(ALIMENT_URL))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath(
                                "$.fieldError[*].fieldName",
                                Matchers.containsInAnyOrder(
                                        "portion",
                                        "totalFat",
                                        "protein",
                                        "carbohydrate",
                                        "fiber",
                                        "sugars"
                                ))
                        )
                        .andExpect(jsonPath(
                                "$.fieldError[*].errorMessage",
                                Matchers.containsInAnyOrder(
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'."
                                ))
                        )
                        .andDo(print());
            }
        }

        @Test
        @DisplayName("Test if throws an exception when try to update an aliment by invalid id and receive status code 404.")
        void cannotUpdateAnAlimentByInvalidId() throws Exception {
            AlimentDto aliment = AlimentFactory.alimentDto();
            long invalidId = 999L;
            String path = ALIMENT_URL + "/" + invalidId;
            String valueAsString = objectMapper.writeValueAsString(aliment);
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value(
                            "class com.anthony.calorie_counter.exceptions.EntityDataNotFoundException"
                    ))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(
                            ExceptionMessages.ALIMENT_NOT_FOUND_WITH_ID + invalidId
                    ))
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when try to update an aliment by invalid name and receive status code 400.")
        void cannotUpdateAnAlimentByInvalidName() throws Exception {
            AlimentDto aliment = AlimentFactory.alimentDto();
            String[] invalidNames = {"a", "a".repeat(51)};
            for(String name : invalidNames) {
                String path = ALIMENT_URL + "/" + savedAliment().getId();
                aliment.setName(name);
                String valueAsString = objectMapper.writeValueAsString(aliment);
                mockMvc.perform(put(path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                                .header("Authorization", adminToken)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.path").value(path))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath("$.fieldError[0].fieldName").value("name"))
                        .andExpect(jsonPath("$.fieldError[0].errorMessage").value(
                                "The name length must be between 2 and 50 characters"
                        ))
                        .andDo(print());
            }
        }

        @Test
        @DisplayName("Test if throws an exception when try to update an aliment by empty values and receive status code 400.")
        void cannotUpdateAnAlimentByEmptyValues() throws Exception {
            AlimentDto alimentDto = new AlimentDto("", "", null, "", "", "", "", "");
            String path = ALIMENT_URL + "/" + savedAliment().getId();
            String valueAsString = objectMapper.writeValueAsString(alimentDto);
            mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception")
                            .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                    .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                    .andExpect(jsonPath(
                            "$.fieldError[*].fieldName",
                            Matchers.containsInAnyOrder(
                                    "name", "name",
                                    "portion", "portion",
                                    "calories",
                                    "totalFat", "totalFat",
                                    "protein", "protein",
                                    "carbohydrate", "carbohydrate",
                                    "fiber", "fiber",
                                    "sugars", "sugars"
                            ))
                    )
                    .andExpect(jsonPath(
                            "$.fieldError[*].errorMessage",
                            Matchers.containsInAnyOrder(
                                    "The name must not be empty.",
                                    "The name length must be between 2 and 50 characters",
                                    "The portion must not be empty.",
                                    "The calories must not be null.",
                                    "The totalFat must not be empty.",
                                    "The protein must not be empty.",
                                    "The carbohydrate must not be empty.",
                                    "The fiber must not be empty.",
                                    "The sugars must not be empty.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                    "The field must have zero or two decimal places, no blank spaces and ends with 'g'."
                            ))
                    )
                    .andDo(print());
        }

        @Test
        @DisplayName("Test if throws an exception when try to update an aliment by invalid values format and receive status code 400.")
        void cannotUpdateAnAlimentByInvalidValuesFormat() throws Exception {
            AlimentDto[] invalidAlimentsDto = {
                    new AlimentDto("Test", "0", 1, "0", "0", "0", "0", "0"),
                    new AlimentDto("Test", "0 g", 10, "5 g", "0 g", "0 g", "3 g", "2 g"),
                    new AlimentDto("Test", "0.1", 8, "3.5", "8.6", "0.0", "9.7", "4.9"),
                    new AlimentDto("Test", "0 .1", 21, "3. 5", "8 . 6", "0  .0", "9.  7", "4  .  9"),
                    new AlimentDto("Test", "0.1 g", 14, "3.5 g", "8.6 g", "0.0 g", "9.7 g", "4.9 g"),
                    new AlimentDto("Test", "0 .1g", 23, "3. 5g", "8 . 6g", "0  .0g", "9.  7g", "4  .  9g"),
                    new AlimentDto("Test", "0.15", 22, "3.57", "8.68", "0.08", "9.72", "4.99"),
                    new AlimentDto("Test", "0.1 5", 34, "3. 57", "8 .68", "0 . 08", "9. 7 2", "4 .9 9"),
                    new AlimentDto("Test", "0.1 5g", 7, "3. 57g", "8 .68g", "0 . 08g", "9. 7 2g", "4 .9 9g"),
                    new AlimentDto("Test", "0.151g", 9, "3.572g", "8.6844g", "0.08578g", "9.7201g", "4.993g")
            };
            for (AlimentDto alimentDto : invalidAlimentsDto) {
                String path = ALIMENT_URL + "/" + savedAliment().getId();
                String valueAsString = objectMapper.writeValueAsString(alimentDto);
                mockMvc.perform(put(path)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(valueAsString)
                                .header("Authorization", adminToken)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.path").value(path))
                        .andExpect(jsonPath("$.exception")
                                .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                        .andExpect(jsonPath("$.fieldError[*]").isNotEmpty())
                        .andExpect(jsonPath(
                                "$.fieldError[*].fieldName",
                                Matchers.containsInAnyOrder(
                                        "portion",
                                        "totalFat",
                                        "protein",
                                        "carbohydrate",
                                        "fiber",
                                        "sugars"
                                ))
                        )
                        .andExpect(jsonPath(
                                "$.fieldError[*].errorMessage",
                                Matchers.containsInAnyOrder(
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'.",
                                        "The field must have zero or two decimal places, no blank spaces and ends with 'g'."
                                ))
                        )
                        .andDo(print());
            }
        }

        @Test
        @DisplayName("Test if throws an exception when try to delete an aliment by invalid id and receive status code 404.")
        void cannotDeleteAnAlimentByInvalidId() throws Exception {
            AlimentDto aliment = AlimentFactory.alimentDto();
            long invalidId = 999L;
            String path = ALIMENT_URL + "/" + invalidId;
            String valueAsString = objectMapper.writeValueAsString(aliment);
            mockMvc.perform(delete(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(valueAsString)
                            .header("Authorization", adminToken)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value(
                            "class com.anthony.calorie_counter.exceptions.EntityDataNotFoundException"
                    ))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(
                            ExceptionMessages.ALIMENT_NOT_FOUND_WITH_ID + invalidId
                    ))
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

        @Test
        @DisplayName("Test if throws an exception when try to find an aliment by invalid id and receive status code 404.")
        void cannotFindAnAlimentByInvalidId() throws Exception {
            long invalidId = 999L;
            String path = ALIMENT_URL + "/" + invalidId;
            mockMvc.perform(get(path).header("Authorization", userToken))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.path").value(path))
                    .andExpect(jsonPath("$.exception").value(
                            "class com.anthony.calorie_counter.exceptions.EntityDataNotFoundException"
                    ))
                    .andExpect(jsonPath("$.errors[*]").isNotEmpty())
                    .andExpect(jsonPath("$.errors[*]").value(
                            ExceptionMessages.ALIMENT_NOT_FOUND_WITH_ID + invalidId
                    ))
                    .andDo(print());
        }
    }
}
