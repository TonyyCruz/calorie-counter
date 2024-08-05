package com.anthony.calorie_counter.integration;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.integration.config.TestBase;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.utils.factories.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserModelControllerIntegrationTest extends TestBase {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test @DisplayName("Test if is possible create a new user and receive status code 201.")
    void canCreateAnNewUser() throws Exception {
        UserCreateDto newUser = UserFactory.createUserDto();
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.fullName").value(newUser.fullName()))
            .andExpect(jsonPath("$.email").value(newUser.email()))
            .andExpect(jsonPath("$.phoneNumber").value(newUser.phoneNumber()))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test @DisplayName("Test if is possible find a user by received id and receive status code 200.")
    void canFindAnUserById() throws Exception {
        UserCreateDto userCreateDto = UserFactory.createUserDto();
        UserModel savedUserModel = userRepository.save(userCreateDto.toEntity());
        String path = USER_URL + "/" + savedUserModel.getId();
        mockMvc.perform(get(path))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName").value(userCreateDto.fullName()))
            .andExpect(jsonPath("$.email").value(userCreateDto.email()))
            .andExpect(jsonPath("$.phoneNumber").value(userCreateDto.phoneNumber()))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test @DisplayName("Test if is possible update a user by received id and receive status code 200.")
    void canUserUpdateAnUserById() throws Exception {
        UserCreateDto userCreateDto = UserFactory.createUserDto();
        UserModel currentUserModel = userRepository.save(userCreateDto.toEntity());
        UserCreateDto updateUser = UserFactory.createUserDto();
        String valueAsString = objectMapper.writeValueAsString(updateUser);
        String path = USER_URL + "/" + currentUserModel.getId();
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fullName").value(updateUser.fullName()))
            .andExpect(jsonPath("$.email").value(updateUser.email()))
            .andExpect(jsonPath("$.phoneNumber").value(updateUser.phoneNumber()))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test @DisplayName("Test if is possible delete a user by received id and receive status code 204.")
    void canDeleteAnUserById() throws Exception {
        UserModel userModel = UserFactory.createUser();
        UserModel savedUserModel = userRepository.save(userModel);
        String path = USER_URL + "/" + savedUserModel.getId();
        mockMvc.perform(delete(path))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$.fullName").doesNotExist())
            .andExpect(jsonPath("$.email").doesNotExist())
            .andExpect(jsonPath("$.phoneNumber").doesNotExist())
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    // ======================================== Error cases ======================================== //

    @Test @DisplayName("Test if throws an exception when try create a user with empty full name and receive status code 400.")
    void cannotCreateAnNewUserWithEmptyFullName() throws Exception {
        UserCreateDto newUser = new UserCreateDto("", "teste@email.com", "123456Aa.", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("The name must not be empty."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user with empty email and receive status code 400.")
    void cannotCreateAnNewUserWithEmptyEmail() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "", "123456Aa.", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("The email must not be empty."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user with invalid email format and receive status code 400.")
    void cannotCreateAnNewUserWithInvalidEmailFormat() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "test", "123456Aa.", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("Invalid Email."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user with already used email and receive status code 400.")
    void cannotCreateAnNewUserWithAlreadyUsedEmail() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "test@email.com", "123456Aa.", "(11) 91991-5500");
        userRepository.save(newUser.toEntity());
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.title").value("Conflicting data, received data already exists in the database."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(409))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.dao.DataIntegrityViolationException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty());
    }

    @Test @DisplayName("Test if throws an exception when try create a user with password smaller than 8 characters and receive status code 400.")
    void cannotCreateAnNewUserWithPasswordSmallerThanEight() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "1234Aa.", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("The password must have at least 8 characters including at least one uppercase, one lowercase and a number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that password not have at least one uppercase character and receive status code 400.")
    void cannotCreateAnNewUserIfPasswordNotHaveAtLeastOneUppercaseCharacter() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "1234aa.", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception").value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]")
                    .value("The password must have at least 8 characters including at least one uppercase, one lowercase and a number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that password not have at least one lowercase character and receive status code 400.")
    void cannotCreateAnNewUserIfPasswordNotHaveAtLeastOneLowercaseCharacter() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "1234AA.", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("The password must have at least 8 characters including at least one uppercase, one lowercase and a number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that password not have at least one number and receive status code 400.")
    void cannotCreateAnNewUserIfPasswordNotHaveAtLeastOneNumber() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass.*", "(11) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.path").value(USER_URL))
            .andExpect(jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("The password must have at least 8 characters including at least one uppercase, one lowercase and a number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user without phone number and receive status code 400.")
    void cannotCreateAnNewUserWithoutPhoneNumber() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass8.*", "");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user with invalid phone number digit and receive status code 400.")
    void cannotCreateAnNewUserWithInvalidPhoneNumberDigit() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "(36) 91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user without invalid phone number digit and receive status code 400.")
    void cannotCreateAnNewUserWithoutPhoneNumberDigit() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "91991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that the phone number not start with 9 and receive status code 400.")
    void cannotCreateAnNewUserWithPhoneNumberNotStartWithNine() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "(36) 31991-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that the firsts phone number digits have less than five digits and receive status code 400.")
    void cannotCreateAnNewUserWithPhoneNumberFirstDigitsIsSmallerThanFive() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "(36) 9191-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that the firsts phone number digits have bigger than five digits and receive status code 400.")
    void cannotCreateAnNewUserWithPhoneNumberFirstDigitsIsBiggerThanFive() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "(36) 919951-5500");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that the lasts phone number digits have less than four digits and receive status code 400.")
    void cannotCreateAnNewUserWithPhoneNumberLastsDigitsIsSmallerThanFour() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "(36) 9191-550");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try create a user that the lasts phone number digits have bigger than four digits and receive status code 400.")
    void cannotCreateAnNewUserWithPhoneNumberLastsDigitsIsBiggerThanFour() throws Exception {
        UserCreateDto newUser = new UserCreateDto("Some Name", "teste@email.com", "MyTestPass9.*", "(36) 919951-55009");
        String valueAsString = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad request, invalid argumentation."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value(USER_URL))
                .andExpect(jsonPath("$.exception")
                        .value("class org.springframework.web.bind.MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("Invalid phone number."));
    }

    @Test @DisplayName("Test if throws an exception when try find a user with invalid id and receive status code 404.")
    void cannotFindAnUserByInvalidId() throws Exception {
        byte invalidId = 9;
        String path = USER_URL + "/" + invalidId;
        mockMvc.perform(get(path))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("Bed request, resource not found."))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.path").value(path))
            .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFoundException"))
            .andExpect(jsonPath("$.details[*]").isNotEmpty())
            .andExpect(jsonPath("$.details[*]").value("User " +  invalidId + " was not found."));

    }

    @Test @DisplayName("Test if throws an exception when try update a user with invalid id and receive status code 404.")
    void cannotUserUpdateAnUserByInvalidId() throws Exception {
        byte invalidId = 9;
        UserCreateDto userCreateDto = UserFactory.createUserDto();
        String valueAsString = objectMapper.writeValueAsString(userCreateDto);
        String path = USER_URL + "/" + invalidId;
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON).content(valueAsString))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Bed request, resource not found."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFoundException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("User " +  invalidId + " was not found."));
    }

    @Test @DisplayName("Test if throws an exception when try delete a user with invalid id and receive status code 404.")
    void cannotDeleteAnUserByInvalidId() throws Exception {
        byte invalidId = 9;
        String path = USER_URL + "/" + invalidId;
        mockMvc.perform(delete(path))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Bed request, resource not found."))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.exception").value("class com.anthony.calorie_counter.exceptions.EntityDataNotFoundException"))
                .andExpect(jsonPath("$.details[*]").isNotEmpty())
                .andExpect(jsonPath("$.details[*]").value("User " +  invalidId + " was not found."));
    }
}
